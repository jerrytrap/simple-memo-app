package com.lunchplay.ui.memo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.usecase.CreateMemo
import com.lunchplay.domain.usecase.DeleteMemo
import com.lunchplay.domain.usecase.EditMemo
import com.lunchplay.domain.usecase.GetMemos
import com.lunchplay.ui.memo.mapper.toUiModel
import com.lunchplay.ui.memo.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemos: GetMemos,
    private val createMemo: CreateMemo,
    private val editMemo: EditMemo,
    private val deleteMemo: DeleteMemo
) : ViewModel() {
    private val disposable = CompositeDisposable()

    val memos: StateFlow<MemoUiState> = getMemos()
        .map { memos ->
            if (memos.isEmpty()) {
                MemoUiState.Empty
            } else {
                MemoUiState.Success(memos.map { it.toUiModel() })
            }
        }.catch {
            MemoUiState.Error
        }
        .stateIn(
            initialValue = MemoUiState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(STOP_TIMEOUT_MILLIS)
        )

    private val _memoCreateUiState = MutableStateFlow<MemoCreateUiState>(MemoCreateUiState.Loading)
    val memoCreateUiState: StateFlow<MemoCreateUiState> = _memoCreateUiState

    private val _memoEditUiState = MutableLiveData<MemoEditUiState>()
    val memoEditUiState: LiveData<MemoEditUiState> = _memoEditUiState

    private val _memoDeleteUiState = MutableLiveData<MemoDeleteUiState>()
    val memoDeleteUiState: LiveData<MemoDeleteUiState> = _memoDeleteUiState

    val memoTitle = MutableLiveData<String>()
    val memoContents = MutableLiveData<String>()

    fun createMemo() {
        val title = memoTitle.value
        val contents = memoContents.value

        if (title.isNullOrEmpty() || contents.isNullOrEmpty()) {
            _memoCreateUiState.value = MemoCreateUiState.Empty
        } else {
            val newMemo = Memo(
                DEFAULT_MEMO_ID,
                title,
                contents,
                LocalDateTime.now().toString()
            )

            viewModelScope.launch {
                try {
                    createMemo(newMemo)
                    _memoCreateUiState.value = MemoCreateUiState.Success
                } catch (e: Exception) {
                    _memoCreateUiState.value = MemoCreateUiState.Fail
                }
            }
        }
    }

    fun editMemo(memo: MemoUiModel) {
        _memoEditUiState.value = MemoEditUiState.Loading
        val title = memoTitle.value
        val contents = memoContents.value

        if (title.isNullOrEmpty() || contents.isNullOrEmpty()) {
            _memoEditUiState.value = MemoEditUiState.Empty
        } else {
            val newMemo = Memo(
                memo.id,
                title,
                contents,
                LocalDateTime.now().toString()
            )

            disposable.add(
                editMemo(newMemo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { _memoEditUiState.value = MemoEditUiState.Success },
                        { _memoEditUiState.value = MemoEditUiState.Fail }
                    )
            )

            clearTextField()
        }
    }

    fun deleteMemo(memo: MemoUiModel) {
        _memoDeleteUiState.value = MemoDeleteUiState.Loading
        disposable.add(
            deleteMemo(memo.toMemo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _memoDeleteUiState.value = MemoDeleteUiState.Success },
                    { _memoDeleteUiState.value = MemoDeleteUiState.Fail }
                )
        )
    }

    fun setTextField(memo: MemoUiModel) {
        memoTitle.value = memo.title
        memoContents.value = memo.contents
    }

    fun memoCreated() {
        _memoCreateUiState.value = MemoCreateUiState.Loading
        clearTextField()
    }

    private fun clearTextField() {
        memoTitle.value = EMPTY_STRING
        memoContents.value = EMPTY_STRING
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object {
        const val EMPTY_STRING = ""
        const val DEFAULT_MEMO_ID = 0
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}
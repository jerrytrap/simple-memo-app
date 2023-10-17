package com.lunchplay.ui.memo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import androidx.paging.rxjava2.cachedIn
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

    private val _memos = MutableLiveData<MemoListUiState>()
    val memos: LiveData<MemoListUiState> = _memos

    private val _memoCreateUiState = MutableLiveData<MemoCreateUiState>()
    val memoCreateUiState: LiveData<MemoCreateUiState> = _memoCreateUiState

    private val _memoEditUiState = MutableLiveData<MemoEditUiState>()
    val memoEditUiState: LiveData<MemoEditUiState> = _memoEditUiState

    private val _memoDeleteUiState = MutableLiveData<MemoDeleteUiState>()
    val memoDeleteUiState: LiveData<MemoDeleteUiState> = _memoDeleteUiState

    val memoTitle = MutableLiveData<String>()
    val memoContents = MutableLiveData<String>()

    init {
        _memos.value = MemoListUiState.Loading
        fetchMemos()
    }

    private fun fetchMemos() {
        disposable.add(
            getMemos()
                .cachedIn(viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> _memos.value = MemoListUiState.Success(result.map{ it.toUiModel() }) },
                    { _memos.value = MemoListUiState.Fail }
                )
        )
    }

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

            disposable.add(
                createMemo(newMemo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { _memoCreateUiState.value = MemoCreateUiState.Success },
                        { _memoCreateUiState.value = MemoCreateUiState.Fail }
                    )
            )
        }
    }

    fun editMemo(memo: MemoUiModel) {
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
        }
    }

    fun deleteMemo(memo: MemoUiModel) {
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

    fun memoCreateMessageShown() {
        _memoCreateUiState.value = MemoCreateUiState.Loading
        clearTextField()
    }

    fun memoEditMessageShown() {
        _memoEditUiState.value = MemoEditUiState.Loading
        clearTextField()
    }

    fun memoDeleteMessageShown() {
        _memoDeleteUiState.value = MemoDeleteUiState.Loading
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
    }
}
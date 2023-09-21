package com.lunchplay.ui.memo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.usecase.CreateMemo
import com.lunchplay.domain.usecase.GetMemos
import com.lunchplay.ui.memo.model.MemoUiState
import com.lunchplay.ui.memo.model.MemoUpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemos: GetMemos,
    private val createMemo: CreateMemo
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _memos = MutableLiveData<MemoUiState>()
    val memos: LiveData<MemoUiState> = _memos

    private val _memoUpdateState = MutableLiveData<MemoUpdateUiState>()
    val memoUpdateState: LiveData<MemoUpdateUiState> = _memoUpdateState

    val editMemoTitle = MutableLiveData<String>()
    val editMemoContents = MutableLiveData<String>()

    init {
        _memos.value = MemoUiState.Loading
        fetchMemos()
    }

    private fun fetchMemos() {
        disposable.add(
            getMemos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    _memos.value = if (result.isEmpty()) {
                        MemoUiState.Empty
                    } else {
                        MemoUiState.Success(result.map{ it.toUiModel() })
                    }
                }, {
                    _memos.value = MemoUiState.Error
                    Log.d("memomemo", it.stackTraceToString())
                })
        )
    }

    fun createMemo() {
        val title = editMemoTitle.value
        val contents = editMemoContents.value

        if (title.isNullOrEmpty() || contents.isNullOrEmpty()) {
            _memoUpdateState.value = MemoUpdateUiState.Empty
            _memoUpdateState.value = MemoUpdateUiState.Ready
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
                        {
                            _memoUpdateState.value = MemoUpdateUiState.Success
                            _memoUpdateState.value = MemoUpdateUiState.Ready
                        },
                        {
                            _memoUpdateState.value = MemoUpdateUiState.Fail
                            _memoUpdateState.value = MemoUpdateUiState.Ready
                        }
                    )
            )

            clearTextField()
        }
    }

    private fun clearTextField() {
        editMemoTitle.value = EMPTY_STRING
        editMemoContents.value = EMPTY_STRING
    }

    companion object {
        const val EMPTY_STRING = ""
        const val DEFAULT_MEMO_ID = 0
    }
}
package com.lunchplay.ui.memo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lunchplay.domain.usecase.GetMemos
import com.lunchplay.ui.memo.model.MemoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemos: GetMemos
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _memos = MutableLiveData<MemoUiState>()
    val memos: LiveData<MemoUiState> = _memos

    init {
        fetchMemos()
    }

    private fun fetchMemos() {
        disposable.add(
            getMemos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    _memos.value = MemoUiState.Success(result)
                }
        )
    }
}
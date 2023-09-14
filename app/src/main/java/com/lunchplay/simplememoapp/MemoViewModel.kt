package com.lunchplay.simplememoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _memos = MutableLiveData<List<Memo>>()
    val memos: LiveData<List<Memo>> = _memos

    init {
        getMemos()
    }

    private fun getMemos() {
        disposable.add(
            memoRepository.getMemos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    _memos.value = result
                }
        )
    }
}
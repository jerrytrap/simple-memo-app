package com.lunchplay.data.memo.source.local

import io.reactivex.Flowable
import javax.inject.Inject

class MemoDataSource @Inject constructor(
    private val memoDao: MemoDao
) {
    fun getMemos(): Flowable<List<MemoEntity>> = memoDao.getMemos()
}
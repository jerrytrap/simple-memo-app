package com.lunchplay.data

import io.reactivex.Flowable
import javax.inject.Inject

class MemoDataSource @Inject constructor(
    private val memoDao: MemoDao
) {
    fun getMemos(): Flowable<List<MemoEntity>> = memoDao.getMemos()
}
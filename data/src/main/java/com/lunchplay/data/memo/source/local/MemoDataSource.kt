package com.lunchplay.data.memo.source.local

import com.lunchplay.data.toMemoEntity
import com.lunchplay.domain.entity.Memo
import io.reactivex.Flowable
import javax.inject.Inject

class MemoDataSource @Inject constructor(
    private val memoDao: MemoDao
) {
    fun getMemos(): Flowable<List<MemoEntity>> = memoDao.getMemos()

    fun createMemo(memo: Memo) = memoDao.createMemo(memo.toMemoEntity())
}
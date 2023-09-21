package com.lunchplay.data.memo.source.local

import com.lunchplay.data.mapper.toMemoEntity
import com.lunchplay.domain.entity.Memo
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val memoDao: MemoDao
) : MemoLocalDataSource {
    override fun getMemos(): Flowable<List<MemoEntity>> = memoDao.getMemos()

    override fun createMemo(memo: Memo) = memoDao.createMemo(memo.toMemoEntity())

    override fun editMemo(memo: Memo): Completable = memoDao.editMemo(memo.toMemoEntity())
}
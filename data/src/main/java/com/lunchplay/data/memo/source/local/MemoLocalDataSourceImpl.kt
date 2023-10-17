package com.lunchplay.data.memo.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val memoDao: MemoDao
) : MemoLocalDataSource {
    override fun getMemos(): Flow<List<MemoEntity>> = memoDao.getMemos()

    override fun createMemo(memo: MemoEntity) = memoDao.createMemo(memo)

    override fun editMemo(memo: MemoEntity) = memoDao.editMemo(memo)

    override fun deleteMemo(memo: MemoEntity) = memoDao.deleteMemo(memo)
}
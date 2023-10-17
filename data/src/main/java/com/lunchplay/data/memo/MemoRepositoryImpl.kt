package com.lunchplay.data.memo

import com.lunchplay.data.mapper.toMemoEntity
import com.lunchplay.data.memo.source.local.MemoLocalDataSource
import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.repository.MemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoLocalDataSource: MemoLocalDataSource
) : MemoRepository {
    override fun getMemos(): Flow<List<Memo>> =
        memoLocalDataSource.getMemos().map { memos ->
            memos.map { it.toMemo() }
        }

    override suspend fun createMemo(memo: Memo) = memoLocalDataSource.createMemo(memo.toMemoEntity())

    override suspend fun editMemo(memo: Memo) = memoLocalDataSource.editMemo(memo.toMemoEntity())

    override fun deleteMemo(memo: Memo) = memoLocalDataSource.deleteMemo(memo.toMemoEntity())
}
package com.lunchplay.data.memo

import com.lunchplay.data.mapper.toMemoEntity
import com.lunchplay.data.memo.source.local.MemoLocalDataSource
import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.repository.MemoRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoLocalDataSource: MemoLocalDataSource
) : MemoRepository {
    override fun getMemos(): Flowable<List<Memo>> =
        memoLocalDataSource.getMemos().flatMap { memoEntities ->
            Flowable.just(memoEntities.map { memoEntity ->
                memoEntity.toMemo()
            })
        }

    override fun createMemo(memo: Memo) = memoLocalDataSource.createMemo(memo.toMemoEntity())

    override fun editMemo(memo: Memo) = memoLocalDataSource.editMemo(memo.toMemoEntity())

    override fun deleteMemo(memo: Memo) = memoLocalDataSource.deleteMemo(memo.toMemoEntity())
}
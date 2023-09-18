package com.lunchplay.data.memo

import com.lunchplay.data.memo.source.local.MemoDataSource
import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.repository.MemoRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoDataSource: MemoDataSource
) : MemoRepository {
    override fun getMemos(): Flowable<List<Memo>> =
        memoDataSource.getMemos().flatMap { memoEntities ->
            Flowable.just(memoEntities.map { memoEntity ->
                memoEntity.toMemo()
            })
        }

    override fun createMemo(memo: Memo): Completable =
        memoDataSource.createMemo(memo)
}
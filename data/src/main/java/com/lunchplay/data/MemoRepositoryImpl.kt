package com.lunchplay.data

import com.lunchplay.domain.Memo
import com.lunchplay.domain.MemoRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MemoRepositoryImpl @Inject constructor(
    private val memoDataSource: MemoDataSource
) : MemoRepository{
    override fun getMemos(): Flowable<List<Memo>> =
        memoDataSource.getMemos().flatMap { memoEntities ->
            Flowable.just(memoEntities.map { memoEntity ->
                memoEntity.toMemo()
            })
        }
}
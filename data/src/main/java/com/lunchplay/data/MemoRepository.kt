package com.lunchplay.data

import com.lunchplay.domain.Memo
import io.reactivex.Flowable
import javax.inject.Inject

class MemoRepository @Inject constructor(
    private val memoDataSource: MemoDataSource
) {
    fun getMemos(): Flowable<List<Memo>> =
        memoDataSource.getMemos().flatMap { memoEntities ->
            Flowable.just(memoEntities.map { memoEntity ->
                memoEntity.toMemo()
            })
        }
}
package com.lunchplay.data.memo.source.local

import com.lunchplay.domain.entity.Memo
import io.reactivex.Completable
import io.reactivex.Flowable

interface MemoLocalDataSource {
    fun getMemos(): Flowable<List<MemoEntity>>

    fun createMemo(memo: Memo): Completable
}
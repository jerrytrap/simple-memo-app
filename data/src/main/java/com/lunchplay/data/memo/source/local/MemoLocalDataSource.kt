package com.lunchplay.data.memo.source.local

import io.reactivex.Completable
import io.reactivex.Flowable

interface MemoLocalDataSource {
    fun getMemos(): Flowable<List<MemoEntity>>

    fun createMemo(memo: MemoEntity): Completable

    fun editMemo(memo: MemoEntity): Completable

    fun deleteMemo(memo: MemoEntity): Completable
}
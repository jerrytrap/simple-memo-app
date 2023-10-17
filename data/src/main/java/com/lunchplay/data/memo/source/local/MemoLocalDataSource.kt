package com.lunchplay.data.memo.source.local

import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

interface MemoLocalDataSource {
    fun getMemos(): Flow<List<MemoEntity>>

    fun createMemo(memo: MemoEntity): Completable

    fun editMemo(memo: MemoEntity): Completable

    fun deleteMemo(memo: MemoEntity): Completable
}
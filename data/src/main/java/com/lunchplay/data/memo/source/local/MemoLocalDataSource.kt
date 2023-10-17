package com.lunchplay.data.memo.source.local

import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

interface MemoLocalDataSource {
    fun getMemos(): Flow<List<MemoEntity>>

    suspend fun createMemo(memo: MemoEntity)

    fun editMemo(memo: MemoEntity): Completable

    fun deleteMemo(memo: MemoEntity): Completable
}
package com.lunchplay.data.memo.source.local

import kotlinx.coroutines.flow.Flow

interface MemoLocalDataSource {
    fun getMemos(): Flow<List<MemoEntity>>

    suspend fun createMemo(memo: MemoEntity)

    suspend fun editMemo(memo: MemoEntity)

    suspend fun deleteMemo(memo: MemoEntity)
}
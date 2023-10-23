package com.lunchplay.data.memo.source.local

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MemoLocalDataSource {
    fun getMemos(): Flow<PagingData<MemoEntity>>

    suspend fun createMemo(memo: MemoEntity)

    suspend fun editMemo(memo: MemoEntity)

    suspend fun deleteMemo(memo: MemoEntity)
}
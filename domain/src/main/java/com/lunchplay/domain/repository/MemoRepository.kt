package com.lunchplay.domain.repository

import androidx.paging.PagingData
import com.lunchplay.domain.entity.Memo
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun getMemos(): Flow<PagingData<Memo>>

    suspend fun createMemo(memo: Memo)

    suspend fun editMemo(memo: Memo)

    suspend fun deleteMemo(memo: Memo)
}
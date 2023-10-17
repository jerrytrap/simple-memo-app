package com.lunchplay.domain.repository

import com.lunchplay.domain.entity.Memo
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

interface MemoRepository {
    fun getMemos(): Flow<List<Memo>>

    suspend fun createMemo(memo: Memo)

    suspend fun editMemo(memo: Memo)

    fun deleteMemo(memo: Memo): Completable
}
package com.lunchplay.data.memo.source.local

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoEntity ORDER BY date DESC")
    fun getMemos(): PagingSource<Int, MemoEntity>

    @Insert
    suspend fun createMemo(memo: MemoEntity)

    @Update
    suspend fun editMemo(memo: MemoEntity)

    @Delete
    suspend fun deleteMemo(memo: MemoEntity)
}
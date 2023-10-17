package com.lunchplay.data.memo.source.local

import androidx.room.*
import io.reactivex.Completable
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoEntity ORDER BY date DESC")
    fun getMemos(): Flow<List<MemoEntity>>

    @Insert
    suspend fun createMemo(memo: MemoEntity)

    @Update
    fun editMemo(memo: MemoEntity): Completable

    @Delete
    fun deleteMemo(memo: MemoEntity): Completable
}
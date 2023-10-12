package com.lunchplay.data.memo.source.local

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Completable

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoEntity ORDER BY date DESC")
    fun getMemos(): PagingSource<Int, MemoEntity>

    @Insert
    fun createMemo(memo: MemoEntity): Completable

    @Update
    fun editMemo(memo: MemoEntity): Completable

    @Delete
    fun deleteMemo(memo: MemoEntity): Completable
}
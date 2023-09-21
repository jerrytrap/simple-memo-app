package com.lunchplay.data.memo.source.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoEntity ORDER BY date DESC")
    fun getMemos(): Flowable<List<MemoEntity>>

    @Insert
    fun createMemo(memo: MemoEntity): Completable

    @Update
    fun editMemo(memo: MemoEntity): Completable

    @Delete
    fun deleteMemo(memo: MemoEntity): Completable
}
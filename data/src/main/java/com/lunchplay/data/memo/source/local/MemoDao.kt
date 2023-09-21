package com.lunchplay.data.memo.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
}
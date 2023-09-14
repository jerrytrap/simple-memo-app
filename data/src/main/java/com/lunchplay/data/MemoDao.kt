package com.lunchplay.data

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoEntity")
    fun getMemos(): Flowable<List<MemoEntity>>
}
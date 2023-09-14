package com.lunchplay.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lunchplay.data.memo.source.local.MemoDao
import com.lunchplay.data.memo.source.local.MemoEntity

@Database(entities = [MemoEntity::class], version = 1)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
}
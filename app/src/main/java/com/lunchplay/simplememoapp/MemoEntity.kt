package com.lunchplay.simplememoapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "contents") val contents: String,
    @ColumnInfo(name = "date") val date: String
) {
    fun toMemo() = Memo(
        id,
        title,
        contents,
        date
    )
}
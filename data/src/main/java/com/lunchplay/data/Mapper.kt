package com.lunchplay.data

import com.lunchplay.data.memo.source.local.MemoEntity
import com.lunchplay.domain.entity.Memo

fun Memo.toMemoEntity() =
    MemoEntity(
        id,
        title,
        contents,
        date
    )
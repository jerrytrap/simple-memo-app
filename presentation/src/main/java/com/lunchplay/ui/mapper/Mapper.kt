package com.lunchplay.ui.mapper

import com.lunchplay.domain.entity.Memo
import com.lunchplay.ui.model.MemoUiModel
import com.lunchplay.ui.util.calcMemoWrittenTime

fun Memo.toUiModel() =
    MemoUiModel(
        id,
        title,
        contents,
        date.calcMemoWrittenTime()
    )
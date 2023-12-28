package com.lunchplay.ui.memo.mapper

import com.lunchplay.domain.entity.Memo
import com.lunchplay.ui.memo.model.MemoUiModel
import com.lunchplay.ui.memo.util.calcMemoEditedTime

fun Memo.toUiModel() =
    MemoUiModel(
        id,
        title,
        contents,
        calcMemoEditedTime(date)
    )
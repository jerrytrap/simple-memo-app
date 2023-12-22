package com.lunchplay.ui.memo.model

import com.lunchplay.domain.entity.Memo
import java.io.Serializable

data class MemoUiModel(
    val id: Int,
    val title: String,
    val contents: String,
    val date: MemoEditedTime
) : Serializable {
    fun toMemo() = Memo(
        id,
        title,
        contents,
        date.toString()
    )
}
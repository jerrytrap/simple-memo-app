package com.lunchplay.ui.memo.model

import java.io.Serializable

data class MemoUiModel(
    val id: Int,
    val title: String,
    val contents: String,
    val date: WrittenTime
) : Serializable
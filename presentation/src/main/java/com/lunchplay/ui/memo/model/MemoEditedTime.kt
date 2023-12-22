package com.lunchplay.ui.memo.model

import java.time.LocalDateTime

data class MemoEditedTime(
    val interval: MemoTimeInterval,
    val dateTime: LocalDateTime
) {
    override fun toString(): String {
        return dateTime.toString()
    }
}

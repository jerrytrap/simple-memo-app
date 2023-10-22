package com.lunchplay.ui.model

import java.time.LocalDateTime

data class WrittenTime(
    val period: Period,
    val dateTime: LocalDateTime
) {
    override fun toString(): String {
        return dateTime.toString()
    }
}

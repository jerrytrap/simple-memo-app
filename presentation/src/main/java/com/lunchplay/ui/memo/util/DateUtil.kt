package com.lunchplay.ui.memo.util

import com.lunchplay.ui.memo.model.MemoTimeInterval
import com.lunchplay.ui.memo.model.MemoEditedTime
import java.time.*

fun calcMemoEditedTime(
    time: String,
    now: LocalDateTime = LocalDateTime.now()
): MemoEditedTime {
    val memoDateTime = LocalDateTime.parse(time)
    val yearDiff = now.year - memoDateTime.year
    val dayDiff = now.dayOfYear - memoDateTime.dayOfYear

    return if (yearDiff > 0) {
        MemoEditedTime(MemoTimeInterval.FEW_YEARS_AGO, memoDateTime)
    } else if (yearDiff == 0 && dayDiff > 0) {
        MemoEditedTime(MemoTimeInterval.THIS_YEAR, memoDateTime)
    } else {
        MemoEditedTime(MemoTimeInterval.TODAY, memoDateTime)
    }
}
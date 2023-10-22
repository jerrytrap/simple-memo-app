package com.lunchplay.ui.util

import com.lunchplay.ui.model.Period
import com.lunchplay.ui.model.WrittenTime
import java.time.*

fun String.calcMemoWrittenTime(): WrittenTime {
    val memoDateTime = LocalDateTime.parse(this)
    val now = LocalDateTime.now()
    val yearDiff = now.year - memoDateTime.year
    val dayDiff = now.dayOfYear - memoDateTime.dayOfYear

    return if (yearDiff > 0) {
        WrittenTime(Period.FEW_YEARS_AGO, memoDateTime)
    } else if (yearDiff == 0 && dayDiff > 0) {
        WrittenTime(Period.THIS_YEAR, memoDateTime)
    } else {
        WrittenTime(Period.TODAY, memoDateTime)
    }
}
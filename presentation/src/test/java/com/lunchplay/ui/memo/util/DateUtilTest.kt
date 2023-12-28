package com.lunchplay.ui.memo.util

import com.lunchplay.ui.memo.model.MemoTimeInterval
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtilTest {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val now = LocalDateTime.parse("2023-12-22 16:10:25", formatter)

    @Test
    fun calcMemoWrittenTime_editedToday_returnToday() {
        val date = "2023-12-22 11:37:10"
        val dateTime = LocalDateTime.parse(date, formatter).toString()
        val value = calcMemoEditedTime(dateTime, now)

        assertEquals(value.interval, MemoTimeInterval.TODAY)
    }

    @Test
    fun calcMemoWrittenTime_editedThisYear_returnThisYear() {
        val date = "2023-06-15 20:22:50"
        val dateTime = LocalDateTime.parse(date, formatter).toString()
        val value = calcMemoEditedTime(dateTime)

        assertEquals(value.interval, MemoTimeInterval.THIS_YEAR)
    }

    @Test
    fun calcMemoWrittenTime_editedLastYear_returnFewYearsAgo() {
        val date = "2022-11-10 10:42:30"
        val dateTime = LocalDateTime.parse(date, formatter).toString()
        val value = calcMemoEditedTime(dateTime)

        assertEquals(value.interval, MemoTimeInterval.FEW_YEARS_AGO)
    }
}
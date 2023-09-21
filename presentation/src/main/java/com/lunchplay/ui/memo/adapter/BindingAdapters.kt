package com.lunchplay.ui.memo.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lunchplay.ui.R
import com.lunchplay.ui.memo.model.Period
import com.lunchplay.ui.memo.model.WrittenTime

@BindingAdapter("writtenTime")
fun bindingWrittenTime(view: TextView, writtenTime: WrittenTime) {
    when(writtenTime.period) {
        Period.FEW_YEARS_AGO -> {
            view.text = view.context.getString(
                R.string.memo_few_years_ago,
                writtenTime.dateTime.year,
                writtenTime.dateTime.monthValue,
                writtenTime.dateTime.dayOfMonth
            )
        }
        Period.THIS_YEAR -> {
            view.text = view.context.getString(
                R.string.memo_this_year,
                writtenTime.dateTime.monthValue,
                writtenTime.dateTime.dayOfMonth
            )
        }
        Period.TODAY -> {
            view.text = view.context.getString(
                R.string.memo_today,
                writtenTime.dateTime.hour,
                writtenTime.dateTime.minute
            )
        }
    }
}
package com.lunchplay.ui.memo.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lunchplay.ui.R
import com.lunchplay.ui.memo.model.MemoTimeInterval
import com.lunchplay.ui.memo.model.MemoEditedTime

@BindingAdapter("editedTime")
fun bindingEditedTime(view: TextView, editedTime: MemoEditedTime) {
    when (editedTime.interval) {
        MemoTimeInterval.FEW_YEARS_AGO -> {
            view.text = view.context.getString(
                R.string.memo_few_years_ago,
                editedTime.dateTime.year,
                editedTime.dateTime.monthValue,
                editedTime.dateTime.dayOfMonth
            )
        }
        MemoTimeInterval.THIS_YEAR -> {
            view.text = view.context.getString(
                R.string.memo_this_year,
                editedTime.dateTime.monthValue,
                editedTime.dateTime.dayOfMonth
            )
        }
        MemoTimeInterval.TODAY -> {
            view.text = view.context.getString(
                R.string.memo_today,
                editedTime.dateTime.hour,
                editedTime.dateTime.minute
            )
        }
    }
}
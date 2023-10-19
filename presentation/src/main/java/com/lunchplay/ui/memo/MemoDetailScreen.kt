package com.lunchplay.ui.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lunchplay.ui.memo.model.MemoUiModel

@Composable
fun MemoDetailScreen(memo: MemoUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = memo.title,
            fontSize = 40.sp,
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = memo.contents,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}
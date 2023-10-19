package com.lunchplay.ui.memo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lunchplay.ui.R
import com.lunchplay.ui.memo.model.MemoListUiState
import com.lunchplay.ui.memo.model.MemoUiModel
import com.lunchplay.ui.memo.model.Period
import com.lunchplay.ui.memo.model.WrittenTime
import java.time.LocalDateTime

@Composable
fun MemoListScreen(
    onItemClick: (MemoUiModel) -> Unit
) {
    val viewModel: MemoViewModel = hiltViewModel()

    val memoListUiState by viewModel.memoListUiState.collectAsState(initial = MemoListUiState.Loading)
    when (memoListUiState) {
        is MemoListUiState.Success -> {
            ShowMemoList(
                memoListUiState = memoListUiState,
                onItemClick = onItemClick
            )
        }
        is MemoListUiState.Empty -> {
            ShowTextAtCenter(R.string.info_new_memo)
        }
        is MemoListUiState.Error -> {
            ShowTextAtCenter(R.string.info_memo_error)
        }
        is MemoListUiState.Loading -> Unit
    }

    ShowMemoCreateButton {
        //Navigate to CreateScreen
    }
}

@Composable
fun ShowMemoList(
    memoListUiState: MemoListUiState,
    onItemClick: (MemoUiModel) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        val data = (memoListUiState as MemoListUiState.Success).memos
        items(data) { item ->
            MemoListItem(
                item = item,
                onclick = { onItemClick(item) }
            )
        }
    }
}

@Composable
fun ShowTextAtCenter(resId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(id = resId),
            fontSize = 20.sp
        )
    }
}

@Composable
fun ShowMemoCreateButton(onclick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Bottom,
        horizontalAlignment = End,
    ) {
        FloatingActionButton(onClick = onclick) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.create_memo)
            )
        }
    }
}

@Composable
fun MemoListItem(
    item: MemoUiModel,
    onclick: () -> Unit
) {
    val dateText = when (item.date.period) {
        Period.FEW_YEARS_AGO -> stringResource(
            id = R.string.memo_few_years_ago,
            item.date.dateTime.year,
            item.date.dateTime.monthValue,
            item.date.dateTime.dayOfMonth
        )
        Period.THIS_YEAR -> stringResource(
            id = R.string.memo_this_year,
            item.date.dateTime.monthValue,
            item.date.dateTime.dayOfMonth
        )
        Period.TODAY -> stringResource(
            id = R.string.memo_today,
            item.date.dateTime.hour,
            item.date.dateTime.minute,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onclick)
            .padding(10.dp),
        horizontalArrangement = SpaceBetween,
    ) {
        Text(
            text = item.title,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = dateText,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
fun PreviewMemoListItem(){
    val sampleMemo = MemoUiModel(
        0,
        "제목",
        "내용",
        WrittenTime(
            Period.TODAY,
            LocalDateTime.now()
        )
    )
    
    MemoListItem(
        item = sampleMemo,
        onclick = { }
    )
}
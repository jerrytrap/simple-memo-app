package com.lunchplay.ui.memo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lunchplay.ui.R
import com.lunchplay.ui.model.MemoUiModel
import com.lunchplay.ui.model.Period
import com.lunchplay.ui.model.WrittenTime
import java.time.LocalDateTime

@Composable
fun MemoListScreen(
    onItemClick: (MemoUiModel) -> Unit,
    onCreateButtonClick: () -> Unit
) {
    val viewModel: MemoViewModel = hiltViewModel()

    val memoList = viewModel.memoList.collectAsLazyPagingItems()

    when (memoList.loadState.append) {
        is LoadState.NotLoading -> {
            if (memoList.itemCount == 0) {
                AlertMessage(resId = R.string.info_new_memo)
            } else {
                MemoList(
                    memoList = memoList,
                    onItemClick = onItemClick
                )
            }
        }
        is LoadState.Error -> {
            AlertMessage(resId = R.string.info_memo_error)
        }
        is LoadState.Loading -> { /* progress bar 표시 */ }
    }

    MemoCreateButton {
        onCreateButtonClick()
    }
}

@Composable
fun MemoList(
    memoList: LazyPagingItems<MemoUiModel>,
    onItemClick: (MemoUiModel) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(memoList.itemCount) { idx->
            val item = memoList[idx]
            if (item != null) {
                MemoListItem(
                    item = item,
                    onclick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun AlertMessage(resId: Int) {
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
fun MemoCreateButton(onclick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Bottom,
        horizontalAlignment = End,
    ) {
        FloatingActionButton(onClick = onclick) {
            Icon(
                imageVector = Icons.Filled.Create,
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
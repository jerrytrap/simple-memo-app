package com.lunchplay.ui.memo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lunchplay.ui.R
import com.lunchplay.ui.memo.model.MemoEditUiState
import com.lunchplay.ui.memo.model.MemoUiModel

@Composable
fun MemoEditScreen(
    viewModel: MemoViewModel = hiltViewModel(),
    onSaveButtonClick: () -> Unit,
    memo: MemoUiModel
) {
    var memoTitle by remember { mutableStateOf(memo.title) }
    var memoContents by remember { mutableStateOf(memo.contents) }
    val memoCreateUiState by viewModel.memoEditUiState.collectAsState(initial = MemoEditUiState.Loading)
    val context = LocalContext.current

    when (memoCreateUiState) {
        is MemoEditUiState.Success -> {
            showToast(context, R.string.memo_edit_success)
            viewModel.memoEditMessageShown()
            onSaveButtonClick()
        }
        is MemoEditUiState.Empty -> {
            showToast(context, R.string.title_or_contents_empty)
            viewModel.memoEditMessageShown()
        }
        is MemoEditUiState.Error -> {
            showToast(context, R.string.memo_edit_fail)
            viewModel.memoEditMessageShown()
        }
        is MemoEditUiState.Loading -> Unit
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column{
            OutlinedTextField(
                modifier = Modifier.padding(10.dp),
                value = memoTitle,
                onValueChange = { memoTitle = it },
                label = { Text(stringResource(id = R.string.title)) }
            )
            OutlinedTextField(
                modifier = Modifier.padding(10.dp),
                value = memoContents,
                onValueChange = { memoContents = it },
                label = { Text(stringResource(id = R.string.contents)) },
                maxLines = 5
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Button(
            modifier = Modifier.padding(30.dp),
            onClick = {
                viewModel.editMemo(memo.id, memoTitle, memoContents)
            }
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}
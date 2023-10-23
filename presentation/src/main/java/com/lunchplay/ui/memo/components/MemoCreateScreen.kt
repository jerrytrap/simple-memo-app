package com.lunchplay.ui.memo.components

import androidx.compose.foundation.layout.*
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
import com.lunchplay.ui.memo.MemoViewModel
import com.lunchplay.ui.model.MemoCreateUiState

@Composable
fun MemoCreateScreen(
    viewModel: MemoViewModel = hiltViewModel(),
    onSaveButtonClick: () -> Unit
) {
    var memoTitle by remember { mutableStateOf("") }
    var memoContents by remember { mutableStateOf("") }
    val memoCreateUiState by viewModel.memoCreateUiState.collectAsState(initial = MemoCreateUiState.Loading)
    val context = LocalContext.current

    when (memoCreateUiState) {
        is MemoCreateUiState.Success -> {
            showToast(context, R.string.memo_create_success)
            viewModel.memoCreateMessageShown()
            onSaveButtonClick()
        }
        is MemoCreateUiState.Empty -> {
            showToast(context, R.string.title_or_contents_empty)
            viewModel.memoCreateMessageShown()
        }
        is MemoCreateUiState.Error -> {
            showToast(context, R.string.memo_create_fail)
            viewModel.memoCreateMessageShown()
        }
        is MemoCreateUiState.Loading -> Unit
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
                viewModel.createMemo(memoTitle, memoContents)
            }
        ) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}
package com.lunchplay.ui.memo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lunchplay.ui.R
import com.lunchplay.ui.memo.MemoViewModel
import com.lunchplay.ui.model.MemoDeleteUiState
import com.lunchplay.ui.model.MemoUiModel

@Composable
fun MemoDetailScreen(
    memo: MemoUiModel,
    onBackButtonClick: () -> Unit,
    onEditMenuClick: () -> Unit,
    onDeleteMenuClick: () -> Unit,
    viewModel: MemoViewModel = hiltViewModel()
) {
    val memoDeleteUiState by viewModel.memoDeleteUiState.collectAsState(initial = MemoDeleteUiState.Loading)
    val context = LocalContext.current

    when (memoDeleteUiState) {
        is MemoDeleteUiState.Success -> {
            showToast(context, R.string.memo_delete_success)
            viewModel.memoDeleteMessageShown()
            onDeleteMenuClick()
        }
        is MemoDeleteUiState.Error -> {
            showToast(context, R.string.memo_delete_fail)
            viewModel.memoDeleteMessageShown()
        }
        is MemoDeleteUiState.Loading -> Unit
    }
    Scaffold(
        topBar = {
            MemoDetailTopAppBar(
                onBackButtonClick = onBackButtonClick,
                onEditMenuClick = onEditMenuClick,
                onDeleteMenuClick = { viewModel.deleteMemo(memo) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = memo.title,
                fontSize = 40.sp,
                modifier = Modifier.padding(20.dp)
            )
            Text(
                text = memo.contents,
                fontSize = 20.sp,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}

@Composable
fun MemoDetailTopAppBar(
    onBackButtonClick: () -> Unit,
    onEditMenuClick: () -> Unit,
    onDeleteMenuClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var openDeleteDialog by remember { mutableStateOf(false) }

    TopAppBar(
        title = {  },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(id = R.string.menu)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(onClick = { onEditMenuClick() }) {
                        Text(stringResource(id = R.string.edit_memo))
                    }
                    DropdownMenuItem(onClick = { openDeleteDialog = true }) {
                        Text(stringResource(id = R.string.delete_memo))
                    }
                }
            }
        }
    )

    when {
        openDeleteDialog -> {
            DeleteDialog(
                onDismissRequest = { openDeleteDialog = false },
                onConfirmation = {
                    openDeleteDialog = false
                    onDeleteMenuClick()
                }
            )
        }
    }
}

@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        title = { Text(stringResource(id = R.string.memo_delete_alert_title)) },
        text = { Text(stringResource(id = R.string.memo_delete_alert_message)) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onConfirmation() }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}
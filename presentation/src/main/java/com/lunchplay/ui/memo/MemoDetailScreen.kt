package com.lunchplay.ui.memo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lunchplay.ui.R
import com.lunchplay.ui.memo.model.MemoUiModel

@Composable
fun MemoDetailScreen(
    memo: MemoUiModel,
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            MemoDetailTopAppBar(
                onBackButtonClick = onBackButtonClick
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
    onBackButtonClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

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
                    DropdownMenuItem(onClick = { /* Navigate to EditMemoScreen */ }) {
                        Text(stringResource(id = R.string.edit_memo))
                    }
                    DropdownMenuItem(onClick = { /* Show DeleteMemo Dialog */ }) {
                        Text(stringResource(id = R.string.delete_memo))
                    }
                }
            }
        }
    )
}
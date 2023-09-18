package com.lunchplay.ui.memo.model

sealed class MemoUpdateUiState {
    object Success : MemoUpdateUiState()

    object Empty : MemoUpdateUiState()

    object Fail : MemoUpdateUiState()

    object Ready : MemoUpdateUiState()
}
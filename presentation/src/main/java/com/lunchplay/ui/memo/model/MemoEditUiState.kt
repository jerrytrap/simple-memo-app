package com.lunchplay.ui.memo.model

sealed class MemoEditUiState {
    object Success : MemoEditUiState()

    object Empty : MemoEditUiState()

    object Error : MemoEditUiState()

    object Loading : MemoEditUiState()
}
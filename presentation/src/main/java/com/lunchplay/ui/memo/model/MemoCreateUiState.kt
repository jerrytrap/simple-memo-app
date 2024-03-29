package com.lunchplay.ui.memo.model

sealed class MemoCreateUiState {
    object Success : MemoCreateUiState()

    object Empty : MemoCreateUiState()

    object Error : MemoCreateUiState()

    object Loading : MemoCreateUiState()
}

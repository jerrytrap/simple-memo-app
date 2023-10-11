package com.lunchplay.ui.memo.model

sealed class MemoDeleteUiState {
    object Success : MemoDeleteUiState()

    object Fail : MemoDeleteUiState()

    object Loading : MemoDeleteUiState()
}

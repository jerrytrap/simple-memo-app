package com.lunchplay.ui.memo.model

sealed class MemoDeleteUiState {
    object Success : MemoDeleteUiState()

    object Error : MemoDeleteUiState()

    object Loading : MemoDeleteUiState()
}

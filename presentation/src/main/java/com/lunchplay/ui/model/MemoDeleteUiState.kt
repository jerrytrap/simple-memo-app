package com.lunchplay.ui.model

sealed class MemoDeleteUiState {
    object Success : MemoDeleteUiState()

    object Error : MemoDeleteUiState()

    object Loading : MemoDeleteUiState()
}

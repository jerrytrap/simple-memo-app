package com.lunchplay.ui.memo.model

sealed class MemoUiState {
    object Loading : MemoUiState()

    object Error : MemoUiState()

    object Empty : MemoUiState()

    data class Success(
        val memos: List<MemoUiModel>
    ) : MemoUiState()
}
package com.lunchplay.ui.model

sealed class MemoListUiState {
    object Loading : MemoListUiState()

    object Error : MemoListUiState()

    object Empty : MemoListUiState()

    data class Success(
        val memos: List<MemoUiModel>
    ) : MemoListUiState()
}
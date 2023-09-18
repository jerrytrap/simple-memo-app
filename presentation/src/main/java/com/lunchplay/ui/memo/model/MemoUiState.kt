package com.lunchplay.ui.memo.model

import com.lunchplay.domain.entity.Memo

sealed class MemoUiState {
    object Loading : MemoUiState()

    object Error : MemoUiState()

    object Empty : MemoUiState()

    data class Success(
        val memos: List<Memo>
    ) : MemoUiState()
}
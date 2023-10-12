package com.lunchplay.ui.memo.model

import androidx.paging.PagingData

sealed class MemoUiState {
    object Loading : MemoUiState()

    object Error : MemoUiState()

    data class Success(
        val memos: PagingData<MemoUiModel>
    ) : MemoUiState()
}
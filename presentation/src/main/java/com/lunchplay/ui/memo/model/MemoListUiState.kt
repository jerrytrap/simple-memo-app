package com.lunchplay.ui.memo.model

import androidx.paging.PagingData

sealed class MemoListUiState {
    object Loading : MemoListUiState()

    object Fail : MemoListUiState()

    data class Success(
        val memos: PagingData<MemoUiModel>
    ) : MemoListUiState()
}
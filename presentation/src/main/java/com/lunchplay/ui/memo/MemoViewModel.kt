package com.lunchplay.ui.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lunchplay.domain.entity.Memo
import com.lunchplay.domain.usecase.CreateMemo
import com.lunchplay.domain.usecase.DeleteMemo
import com.lunchplay.domain.usecase.EditMemo
import com.lunchplay.domain.usecase.GetMemos
import com.lunchplay.ui.memo.mapper.toUiModel
import com.lunchplay.ui.memo.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val getMemos: GetMemos,
    private val createMemo: CreateMemo,
    private val editMemo: EditMemo,
    private val deleteMemo: DeleteMemo
) : ViewModel() {
    val memoListUiState: StateFlow<MemoListUiState> = getMemos()
        .map { memos ->
            if (memos.isEmpty()) {
                MemoListUiState.Empty
            } else {
                MemoListUiState.Success(memos.map { it.toUiModel() })
            }
        }.catch {
            MemoListUiState.Error
        }.stateIn(
            initialValue = MemoListUiState.Loading,
            scope = viewModelScope,
            started = WhileSubscribed(STOP_TIMEOUT_MILLIS)
        )

    private val _memoCreateUiState = MutableStateFlow<MemoCreateUiState>(MemoCreateUiState.Loading)
    val memoCreateUiState: StateFlow<MemoCreateUiState> = _memoCreateUiState

    private val _memoEditUiState = MutableStateFlow<MemoEditUiState>(MemoEditUiState.Loading)
    val memoEditUiState: StateFlow<MemoEditUiState> = _memoEditUiState

    private val _memoDeleteUiState = MutableStateFlow<MemoDeleteUiState>(MemoDeleteUiState.Loading)
    val memoDeleteUiState: StateFlow<MemoDeleteUiState> = _memoDeleteUiState

    val memoTitle = MutableStateFlow(EMPTY_STRING)
    val memoContents = MutableStateFlow(EMPTY_STRING)

    fun createMemo() {
        val title = memoTitle.value
        val contents = memoContents.value

        if (title.isEmpty() || contents.isEmpty()) {
            _memoCreateUiState.value = MemoCreateUiState.Empty
        } else {
            val newMemo = Memo(
                DEFAULT_MEMO_ID,
                title,
                contents,
                LocalDateTime.now().toString()
            )

            viewModelScope.launch {
                try {
                    createMemo(newMemo)
                    _memoCreateUiState.value = MemoCreateUiState.Success
                } catch (e: Exception) {
                    _memoCreateUiState.value = MemoCreateUiState.Error
                }
            }
        }
    }

    fun editMemo(memo: MemoUiModel) {
        val title = memoTitle.value
        val contents = memoContents.value

        if (title.isEmpty() || contents.isEmpty()) {
            _memoEditUiState.value = MemoEditUiState.Empty
        } else {
            val newMemo = Memo(
                memo.id,
                title,
                contents,
                LocalDateTime.now().toString()
            )

            viewModelScope.launch {
                try {
                    editMemo(newMemo)
                    _memoEditUiState.value = MemoEditUiState.Success
                } catch (e: Exception) {
                    _memoEditUiState.value = MemoEditUiState.Error
                }
            }
        }
    }

    fun deleteMemo(memo: MemoUiModel) {
        viewModelScope.launch {
            try {
                deleteMemo(memo.toMemo())
                _memoDeleteUiState.value = MemoDeleteUiState.Success
            } catch (e: Exception) {
                _memoDeleteUiState.value = MemoDeleteUiState.Error
            }
        }
    }

    fun setTextField(memo: MemoUiModel) {
        memoTitle.value = memo.title
        memoContents.value = memo.contents
    }

    fun memoCreateMessageShown() {
        _memoCreateUiState.value = MemoCreateUiState.Loading
        clearTextField()
    }

    fun memoEditMessageShown() {
        _memoEditUiState.value = MemoEditUiState.Loading
        clearTextField()
    }

    fun memoDeleteMessageShown() {
        _memoDeleteUiState.value = MemoDeleteUiState.Loading
        clearTextField()
    }

    private fun clearTextField() {
        memoTitle.value = EMPTY_STRING
        memoContents.value = EMPTY_STRING
    }

    companion object {
        const val EMPTY_STRING = ""
        const val DEFAULT_MEMO_ID = 0
        const val STOP_TIMEOUT_MILLIS = 5000L
    }
}
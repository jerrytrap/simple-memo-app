package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoCreateBinding
import com.lunchplay.ui.memo.base.BaseFragment
import com.lunchplay.ui.memo.model.MemoCreateUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MemoCreateFragment : BaseFragment<FragmentMemoCreateBinding>(R.layout.fragment_memo_create) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        addSaveButtonClickListener()
        observeUpdateState()
    }

    private fun addSaveButtonClickListener() {
        binding.buttonSave.setOnClickListener {
            viewModel.createMemo()
        }
    }

    private fun observeUpdateState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.memoCreateUiState.collectLatest { state ->
                    when (state) {
                        is MemoCreateUiState.Success -> {
                            showToast(R.string.memo_create_success)
                            findNavController().popBackStack()
                            viewModel.memoCreateMessageShown()
                        }
                        is MemoCreateUiState.Empty -> {
                            showToast(R.string.title_or_contents_empty)
                            viewModel.memoCreateMessageShown()
                        }
                        is MemoCreateUiState.Error -> {
                            showToast(R.string.memo_create_fail)
                            viewModel.memoCreateMessageShown()
                        }
                        is MemoCreateUiState.Loading -> Unit
                    }
                }
            }
        }
    }
}
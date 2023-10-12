package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoCreateBinding
import com.lunchplay.ui.memo.base.BaseFragment
import com.lunchplay.ui.memo.model.MemoCreateUiState

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
        viewModel.memoCreateUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MemoCreateUiState.Success -> {
                    showToast(R.string.memo_create_success)
                    viewModel.memoCreated()
                    findNavController().popBackStack()
                }
                is MemoCreateUiState.Empty -> {
                    showToast(R.string.title_or_contents_empty)
                }
                is MemoCreateUiState.Fail -> {
                    showToast(R.string.memo_create_fail)
                }
                is MemoCreateUiState.Loading -> Unit
            }
        }
    }
}
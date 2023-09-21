package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoCreateBinding
import com.lunchplay.ui.memo.base.BaseFragment
import com.lunchplay.ui.memo.model.MemoUpdateUiState

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
        viewModel.memoUpdateState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MemoUpdateUiState.Success -> {
                    showToast(R.string.memo_create_success)
                    findNavController().popBackStack()
                }
                is MemoUpdateUiState.Empty -> {
                    showToast(R.string.title_or_contents_empty)
                }
                is MemoUpdateUiState.Fail -> {
                    showToast(R.string.memo_create_fail)
                }
                is MemoUpdateUiState.Ready -> Unit
            }
        }
    }
}
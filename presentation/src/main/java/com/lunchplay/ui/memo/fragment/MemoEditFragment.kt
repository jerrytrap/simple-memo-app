package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoEditBinding
import com.lunchplay.ui.memo.base.BaseFragment
import com.lunchplay.ui.memo.model.MemoUiModel
import com.lunchplay.ui.memo.model.MemoEditUiState

class MemoEditFragment : BaseFragment<FragmentMemoEditBinding>(R.layout.fragment_memo_edit) {
    private val args: MemoDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        args.apply {
            viewModel.setTextField(memo)
            addSaveButtonClickListener(memo)
        }
        observeUpdateState()
    }

    private fun addSaveButtonClickListener(memo: MemoUiModel) {
        binding.buttonSave.setOnClickListener {
            viewModel.editMemo(memo)
        }
    }

    private fun observeUpdateState() {
        viewModel.memoEditUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MemoEditUiState.Success -> {
                    showToast(R.string.memo_edit_success)
                    viewModel.memoEditMessageShown()
                    findNavController().popBackStack(R.id.memoListFragment, false)
                }
                is MemoEditUiState.Empty -> {
                    showToast(R.string.title_or_contents_empty)
                    viewModel.memoEditMessageShown()
                }
                is MemoEditUiState.Fail -> {
                    showToast(R.string.memo_edit_fail)
                    viewModel.memoEditMessageShown()
                }
                is MemoEditUiState.Loading -> Unit
            }
        }
    }
}
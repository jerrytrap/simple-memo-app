package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoDetailBinding
import com.lunchplay.ui.memo.base.BaseFragment
import com.lunchplay.ui.memo.model.MemoDeleteUiState

class MemoDetailFragment : BaseFragment<FragmentMemoDetailBinding>(R.layout.fragment_memo_detail) {
    private val args: MemoDetailFragmentArgs by navArgs()
    private lateinit var dialog: MaterialAlertDialogBuilder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.memo = args.memo
        setOverflowMenu()
        observeUpdateState()
        setDialog()
    }

    private fun setOverflowMenu() {
        binding.topAppBar.inflateMenu(R.menu.overflow_menu)
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.option_edit -> {
                    val action = MemoDetailFragmentDirections.actionMemoDetailFragmentToMemoEditFragment(args.memo)
                    findNavController().navigate(action)
                    true
                }
                R.id.option_delete -> {
                    dialog.show()
                    true
                }
                else -> false
            }
        }
    }

    private fun observeUpdateState() {
        viewModel.memoDeleteUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MemoDeleteUiState.Success -> {
                    showToast(R.string.memo_delete_success)
                    viewModel.memoDeleteMessageShown()
                    findNavController().popBackStack()
                }
                is MemoDeleteUiState.Fail -> {
                    showToast(R.string.memo_delete_fail)
                    viewModel.memoDeleteMessageShown()
                }
                is MemoDeleteUiState.Loading -> Unit
            }
        }
    }

    private fun setDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.memo_delete_alert_title))
            .setMessage(resources.getString(R.string.memo_delete_alert_message))
            .setNegativeButton(resources.getString(R.string.dismiss)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.confirm)) { _, _ ->
                viewModel.deleteMemo(args.memo)
            }
    }
}
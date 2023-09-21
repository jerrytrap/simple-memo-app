package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoDetailBinding
import com.lunchplay.ui.memo.MemoViewModel
import com.lunchplay.ui.memo.model.MemoUpdateUiState

class MemoDetailFragment : Fragment() {
    private lateinit var binding: FragmentMemoDetailBinding
    private val viewModel: MemoViewModel by activityViewModels()
    private val args: MemoDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_memo_detail, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.memo = args.memo
        setOverflowMenu()
        observeUpdateState()
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
                    viewModel.deleteMemo(args.memo)
                    true
                }
                else -> false
            }
        }
    }

    private fun observeUpdateState() {
        viewModel.memoUpdateState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MemoUpdateUiState.Success -> {
                    showToast(R.string.memo_delete_success)
                    findNavController().popBackStack()
                }
                is MemoUpdateUiState.Fail -> {
                    showToast(R.string.memo_delete_fail)
                }
                else -> Unit
            }
        }
    }

    private fun showToast(@StringRes id: Int) {
        Toast.makeText(context, resources.getString(id), Toast.LENGTH_SHORT).show()
    }
}
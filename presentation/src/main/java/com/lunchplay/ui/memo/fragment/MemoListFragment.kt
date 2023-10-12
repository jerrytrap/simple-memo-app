package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoListBinding
import com.lunchplay.ui.memo.adapter.MemoAdapter
import com.lunchplay.ui.memo.base.BaseFragment
import com.lunchplay.ui.memo.model.MemoUiModel
import com.lunchplay.ui.memo.model.MemoUiState

class MemoListFragment : BaseFragment<FragmentMemoListBinding>(R.layout.fragment_memo_list) {
    private val adapter = MemoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setItemClickListener()
        addCreateMemoButtonClickListener()
    }

    private fun setRecyclerView() {
        binding.recyclerViewMemoList.apply {
            adapter = this@MemoListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.memos.observe(viewLifecycleOwner) { result ->
            binding.progressIndicatorMemoList.isVisible = result is MemoUiState.Loading
            binding.recyclerViewMemoList.isVisible = result is MemoUiState.Success
            binding.textViewError.isVisible = result is MemoUiState.Error

            if (result is MemoUiState.Success) {
                adapter.submitData(lifecycle, result.memos)
            }
        }

        adapter.addLoadStateListener {
            if (it.append.endOfPaginationReached) {
                binding.textViewEmpty.isVisible = adapter.itemCount == 0
            } else {
                binding.textViewEmpty.isVisible = false
            }
        }
    }

    private fun setItemClickListener() {
        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(memo: MemoUiModel) {
                val action = MemoListFragmentDirections.actionMemoListFragmentToMemoDetailFragment(memo)
                findNavController().navigate(action)
            }
        })
    }

    private fun addCreateMemoButtonClickListener() {
        binding.floatingActionButtonCreateMemo.setOnClickListener {
            val action = MemoListFragmentDirections.actionMemoListFragmentToMemoEditFragment()
            findNavController().navigate(action)
        }
    }
}
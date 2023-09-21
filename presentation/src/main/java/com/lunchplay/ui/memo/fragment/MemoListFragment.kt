package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoListBinding
import com.lunchplay.ui.memo.MemoViewModel
import com.lunchplay.ui.memo.adapter.MemoAdapter
import com.lunchplay.ui.memo.model.MemoUiModel
import com.lunchplay.ui.memo.model.MemoUiState

class MemoListFragment : Fragment() {
    private lateinit var binding: FragmentMemoListBinding
    private val adapter = MemoAdapter()
    private val viewModel: MemoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_memo_list, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
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
            binding.textViewEmpty.isVisible = result is MemoUiState.Empty
            binding.textViewError.isVisible = result is MemoUiState.Error

            if (result is MemoUiState.Success) {
                adapter.submitList(result.memos)
            }
        }

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
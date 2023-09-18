package com.lunchplay.ui.memo

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
import com.lunchplay.domain.entity.Memo
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoListBinding
import com.lunchplay.ui.memo.adapter.MemoAdapter
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
    }

    private fun setRecyclerView() {
        binding.recyclerViewMemoList.apply {
            adapter = this@MemoListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.memos.observe(viewLifecycleOwner) { result ->
            binding.progressIndicatorMemoList.isVisible = result is MemoUiState.Loading
            binding.recyclerViewMemoList.isVisible = result is MemoUiState.Success

            if(result is MemoUiState.Success) {
                adapter.submitList(result.memos)
            }
        }

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(memo: Memo) {
                val action = MemoListFragmentDirections.actionMemoListFragmentToMemoDetailFragment(memo)
                findNavController().navigate(action)
            }
        })
    }
}
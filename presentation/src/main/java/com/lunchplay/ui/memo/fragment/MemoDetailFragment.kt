package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoDetailBinding
import com.lunchplay.ui.memo.model.MemoUiModel

class MemoDetailFragment : Fragment() {
    private lateinit var binding: FragmentMemoDetailBinding

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

        val args: MemoDetailFragmentArgs by navArgs()
        binding.memo = args.memo

        addEditButtonClickListener(args.memo)
    }

    private fun addEditButtonClickListener(memo: MemoUiModel) {
        binding.buttonEdit.setOnClickListener {
            val action = MemoDetailFragmentDirections.actionMemoDetailFragmentToMemoEditFragment(memo)
            findNavController().navigate(action)
        }
    }
}
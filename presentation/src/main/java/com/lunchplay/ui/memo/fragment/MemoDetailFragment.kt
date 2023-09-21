package com.lunchplay.ui.memo.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoDetailBinding

class MemoDetailFragment : Fragment() {
    private lateinit var binding: FragmentMemoDetailBinding
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
                else -> false
            }
        }
    }
}
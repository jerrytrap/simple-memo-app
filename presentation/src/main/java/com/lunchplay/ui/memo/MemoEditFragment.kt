package com.lunchplay.ui.memo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.FragmentMemoEditBinding
import com.lunchplay.ui.memo.model.MemoUpdateUiState

class MemoEditFragment : Fragment() {
    private lateinit var binding: FragmentMemoEditBinding
    private val viewModel: MemoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, R.layout.fragment_memo_edit, container, false
        )
        return binding.root
    }

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

    private fun showToast(@StringRes id: Int) {
        Toast.makeText(context, resources.getString(id), Toast.LENGTH_SHORT).show()
    }
}
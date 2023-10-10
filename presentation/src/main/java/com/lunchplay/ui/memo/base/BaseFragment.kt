package com.lunchplay.ui.memo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lunchplay.ui.memo.MemoViewModel

abstract class BaseFragment<T: ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {
    protected lateinit var binding: T
    open val viewModel: MemoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater, layoutRes, container, false
        )
        return binding.root
    }

    fun showToast(@StringRes id: Int) {
        Toast.makeText(context, resources.getString(id), Toast.LENGTH_SHORT).show()
    }
}
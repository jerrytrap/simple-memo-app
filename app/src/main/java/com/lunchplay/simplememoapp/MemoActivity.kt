package com.lunchplay.simplememoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lunchplay.simplememoapp.databinding.ActivityMemoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding
    private val viewModel: MemoViewModel by viewModels()
    private val adapter = MemoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()

        setRecyclerView()
    }

    private fun setBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_memo, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun setRecyclerView() {
        binding.recyclerViewMemoList.apply {
            this.adapter = this@MemoActivity.adapter
            this.layoutManager = LinearLayoutManager(this@MemoActivity)
        }

        viewModel.memos.observe(this) { result ->
            adapter.submitList(result)
        }
    }
}
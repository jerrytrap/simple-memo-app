package com.lunchplay.simplememoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lunchplay.simplememoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = MemoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()

        setRecyclerView()
    }

    private fun setBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun setRecyclerView() {
        binding.recyclerViewMemoList.apply {
            this.adapter = this@MainActivity.adapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
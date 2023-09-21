package com.lunchplay.ui.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lunchplay.ui.R
import com.lunchplay.ui.databinding.ActivityMemoBinding
import com.lunchplay.ui.memo.fragment.MemoListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setFragment()
    }

    private fun setBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_memo, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction().add(binding.navHostFragment.id, MemoListFragment())
    }
}
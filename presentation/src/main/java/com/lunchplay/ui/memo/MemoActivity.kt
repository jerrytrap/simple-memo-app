package com.lunchplay.ui.memo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.lunchplay.ui.memo.components.MemoApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MemoApp()
        }
    }
}
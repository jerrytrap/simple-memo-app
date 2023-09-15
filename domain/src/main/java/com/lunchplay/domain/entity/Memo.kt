package com.lunchplay.domain.entity

import java.io.Serializable

data class Memo(
    val id: Int,
    val title: String,
    val contents: String,
    val date: String
) : Serializable
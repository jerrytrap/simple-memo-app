package com.lunchplay.domain.repository

import com.lunchplay.domain.entity.Memo
import io.reactivex.Flowable

interface MemoRepository {
    fun getMemos(): Flowable<List<Memo>>
}
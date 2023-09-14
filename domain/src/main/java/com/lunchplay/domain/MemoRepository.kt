package com.lunchplay.domain

import io.reactivex.Flowable

interface MemoRepository {
    fun getMemos(): Flowable<List<Memo>>
}
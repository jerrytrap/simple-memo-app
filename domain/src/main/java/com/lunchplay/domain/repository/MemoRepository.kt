package com.lunchplay.domain.repository

import com.lunchplay.domain.entity.Memo
import io.reactivex.Completable
import io.reactivex.Flowable

interface MemoRepository {
    fun getMemos(): Flowable<List<Memo>>

    fun createMemo(memo: Memo): Completable

    fun editMemo(memo: Memo): Completable
}
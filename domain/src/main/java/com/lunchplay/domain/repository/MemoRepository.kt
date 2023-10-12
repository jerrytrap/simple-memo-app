package com.lunchplay.domain.repository

import androidx.paging.PagingData
import com.lunchplay.domain.entity.Memo
import io.reactivex.Completable
import io.reactivex.Flowable

interface MemoRepository {
    fun getMemos(): Flowable<PagingData<Memo>>

    fun createMemo(memo: Memo): Completable

    fun editMemo(memo: Memo): Completable

    fun deleteMemo(memo: Memo): Completable
}
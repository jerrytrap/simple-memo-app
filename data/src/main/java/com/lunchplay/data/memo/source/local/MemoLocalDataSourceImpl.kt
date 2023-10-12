package com.lunchplay.data.memo.source.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import io.reactivex.Flowable
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val memoDao: MemoDao
) : MemoLocalDataSource {
    override fun getMemos(): Flowable<PagingData<MemoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = DB_LOAD_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { memoDao.getMemos() }
        ).flowable
    }

    override fun createMemo(memo: MemoEntity) = memoDao.createMemo(memo)

    override fun editMemo(memo: MemoEntity) = memoDao.editMemo(memo)

    override fun deleteMemo(memo: MemoEntity) = memoDao.deleteMemo(memo)

    companion object {
        const val DB_LOAD_SIZE = 100
    }
}
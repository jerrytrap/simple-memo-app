package com.lunchplay.data.memo.source.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemoLocalDataSourceImpl @Inject constructor(
    private val memoDao: MemoDao
) : MemoLocalDataSource {
    override fun getMemos(): Flow<PagingData<MemoEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = DB_LOAD_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { memoDao.getMemos() }
        ).flow

    override suspend fun createMemo(memo: MemoEntity) = memoDao.createMemo(memo)

    override suspend fun editMemo(memo: MemoEntity) = memoDao.editMemo(memo)

    override suspend fun deleteMemo(memo: MemoEntity) = memoDao.deleteMemo(memo)

    companion object {
        const val DB_LOAD_SIZE = 100
    }
}
package com.lunchplay.simplememoapp

import android.content.Context
import androidx.room.Room
import com.lunchplay.data.memo.source.local.MemoDao
import com.lunchplay.data.memo.source.local.MemoLocalDataSourceImpl
import com.lunchplay.data.database.MemoDatabase
import com.lunchplay.data.memo.MemoRepositoryImpl
import com.lunchplay.data.memo.source.local.MemoLocalDataSource
import com.lunchplay.domain.repository.MemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {
    private const val MEMO_DATABASE_NAME = "MEMO"

    @Singleton
    @Provides
    fun provideMemoDao(memoDatabase: MemoDatabase) = memoDatabase.memoDao()

    @Singleton
    @Provides
    fun provideMemoDatabase(@ApplicationContext applicationContext: Context) =
        Room.databaseBuilder(
            applicationContext,
            MemoDatabase::class.java,
            MEMO_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideMemoDatasource(memoDao: MemoDao): MemoLocalDataSource =
        MemoLocalDataSourceImpl(memoDao)

    @Singleton
    @Provides
    fun provideMemoRepository(memoLocalDataSourceImpl: MemoLocalDataSourceImpl): MemoRepository =
        MemoRepositoryImpl(memoLocalDataSourceImpl)
}
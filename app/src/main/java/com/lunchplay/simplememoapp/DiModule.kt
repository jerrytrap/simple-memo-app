package com.lunchplay.simplememoapp

import android.content.Context
import androidx.room.Room
import com.lunchplay.data.MemoDao
import com.lunchplay.data.MemoDataSource
import com.lunchplay.data.MemoDatabase
import com.lunchplay.data.MemoRepository
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
    fun provideMemoDatasource(memoDao: MemoDao) =
        MemoDataSource(memoDao)

    @Singleton
    @Provides
    fun provideMemoRepository(memoDataSource: MemoDataSource) =
        MemoRepository(memoDataSource)
}
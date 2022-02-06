package com.example.dailystandup.presentation.di.core

import com.example.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource
import com.example.dailystandup.data.repository.meeting.datasourceimpl.MeetingCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheDataModule {

    @Singleton
    @Provides
    fun provideMeetingCacheDataSource(): MeetingCacheDataSource =
        MeetingCacheDataSourceImpl()
}
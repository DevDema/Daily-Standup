package com.example.dailystandup.presentation.di

import com.example.dailystandup.data.local.repository.meeting.datasource.MeetingCacheDataSource
import com.example.dailystandup.data.local.repository.meeting.datasourceimpl.MeetingCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {

    @Singleton
    @Provides
    fun provideMeetingCacheDataSource(): MeetingCacheDataSource =
        MeetingCacheDataSourceImpl()
}
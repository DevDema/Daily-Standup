package com.example.dailystandup.presentation.di.core

import com.example.dailystandup.data.local.repository.meeting.MeetingRepositoryImpl
import com.example.dailystandup.data.local.repository.meeting.datasource.MeetingCacheDataSource
import com.example.dailystandup.data.local.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.domain.repository.MeetingRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMeetingRepository(
        meetingCacheDataSource: MeetingCacheDataSource,
        meetingLocalDataSource: MeetingLocalDataSource
    ): MeetingRepository = MeetingRepositoryImpl(meetingCacheDataSource, meetingLocalDataSource)
}
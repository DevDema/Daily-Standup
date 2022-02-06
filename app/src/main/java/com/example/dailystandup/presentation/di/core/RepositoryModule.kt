package com.example.dailystandup.presentation.di.core

import com.example.dailystandup.data.repository.meeting.MeetingRepositoryImpl
import com.example.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource
import com.example.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.domain.repository.MeetingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMeetingRepository(
        meetingCacheDataSource: MeetingCacheDataSource,
        meetingLocalDataSource: MeetingLocalDataSource
    ): MeetingRepository = MeetingRepositoryImpl(meetingCacheDataSource, meetingLocalDataSource)
}
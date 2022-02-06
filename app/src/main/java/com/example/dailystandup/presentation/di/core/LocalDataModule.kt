package com.example.dailystandup.presentation.di.core

import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.data.local.repository.meeting.datasourceimpl.MeetingLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideMeetingLocalDataSource(meetingDao: MeetingDao): MeetingLocalDataSource =
        MeetingLocalDataSourceImpl(meetingDao)

}
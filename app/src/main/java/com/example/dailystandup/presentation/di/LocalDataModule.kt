package com.example.dailystandup.presentation.di

import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.data.local.repository.meeting.datasourceimpl.MeetingLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideMeetingLocalDataSource(meetingDao: MeetingDao): MeetingLocalDataSource =
        MeetingLocalDataSourceImpl(meetingDao)

}
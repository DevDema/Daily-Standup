package com.andreadematteis.dailystandup.presentation.di.core

import com.andreadematteis.dailystandup.data.local.dao.MeetingDao
import com.andreadematteis.dailystandup.data.local.dao.TeamMemberDao
import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.andreadematteis.dailystandup.data.repository.meeting.datasourceimpl.MeetingLocalDataSourceImpl
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource
import com.andreadematteis.dailystandup.data.repository.teammember.datasourceimpl.TeamMemberLocalDataSourceImpl
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

    @Singleton
    @Provides
    fun provideTeamMemberLocalDataSource(teamMemberDao: TeamMemberDao): TeamMemberLocalDataSource =
        TeamMemberLocalDataSourceImpl(teamMemberDao)
}
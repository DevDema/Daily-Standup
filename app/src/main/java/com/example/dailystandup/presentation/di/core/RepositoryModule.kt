package com.example.dailystandup.presentation.di.core

import com.example.dailystandup.data.repository.meeting.MeetingRepositoryImpl
import com.example.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource
import com.example.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.data.repository.team.TeamRepositoryImpl
import com.example.dailystandup.data.repository.team.datasource.TeamCacheDataSource
import com.example.dailystandup.data.repository.team.datasource.TeamLocalDataSource
import com.example.dailystandup.data.repository.teammember.TeamMemberRepositoryImpl
import com.example.dailystandup.data.repository.teammember.datasource.TeamMemberCacheDataSource
import com.example.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource
import com.example.dailystandup.domain.repository.MeetingRepository
import com.example.dailystandup.domain.repository.TeamMemberRepository
import com.example.dailystandup.domain.repository.TeamRepository
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
    ): MeetingRepository =
        MeetingRepositoryImpl(meetingCacheDataSource, meetingLocalDataSource)

    @Singleton
    @Provides
    fun provideTeamMemberRepository(
        teamMemberCacheDataSource: TeamMemberCacheDataSource,
        teamMemberLocalDataSource: TeamMemberLocalDataSource
    ): TeamMemberRepository =
        TeamMemberRepositoryImpl(teamMemberCacheDataSource, teamMemberLocalDataSource)

    @Singleton
    @Provides
    fun provideTeamRepository(
        teamCacheDataSource: TeamCacheDataSource,
        teamLocalDataSource: TeamLocalDataSource
    ): TeamRepository =
        TeamRepositoryImpl(teamCacheDataSource, teamLocalDataSource)
}
package com.andreadematteis.dailystandup.presentation.di.core

import com.andreadematteis.dailystandup.data.repository.meeting.MeetingRepositoryImpl
import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource
import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.andreadematteis.dailystandup.data.repository.meetingteammember.MeetingTeamMemberRepositoryImpl
import com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberCacheDataSource
import com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberLocalDataSource
import com.andreadematteis.dailystandup.data.repository.team.TeamRepositoryImpl
import com.andreadematteis.dailystandup.data.repository.team.datasource.TeamCacheDataSource
import com.andreadematteis.dailystandup.data.repository.team.datasource.TeamLocalDataSource
import com.andreadematteis.dailystandup.data.repository.teammember.TeamMemberRepositoryImpl
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberCacheDataSource
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource
import com.andreadematteis.dailystandup.domain.repository.MeetingRepository
import com.andreadematteis.dailystandup.domain.repository.MeetingTeamMemberRepository
import com.andreadematteis.dailystandup.domain.repository.TeamMemberRepository
import com.andreadematteis.dailystandup.domain.repository.TeamRepository
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

    @Singleton
    @Provides
    fun provideMeetingTeamMemberRepository(
        meetingTeamMemberCacheDataSource: MeetingTeamMemberCacheDataSource,
        meetingTeamMemberLocalDataSource: MeetingTeamMemberLocalDataSource
    ): MeetingTeamMemberRepository =
        MeetingTeamMemberRepositoryImpl(
            meetingTeamMemberCacheDataSource,
            meetingTeamMemberLocalDataSource
        )
}
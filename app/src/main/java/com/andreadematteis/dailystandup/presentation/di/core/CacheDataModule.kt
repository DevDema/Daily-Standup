package com.andreadematteis.dailystandup.presentation.di.core

import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource
import com.andreadematteis.dailystandup.data.repository.meeting.datasourceimpl.MeetingCacheDataSourceImpl
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberCacheDataSource
import com.andreadematteis.dailystandup.data.repository.teammember.datasourceimpl.TeamMemberCacheDataSourceImpl
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

    @Singleton
    @Provides
    fun provideTeamMemberCacheDataSource(): TeamMemberCacheDataSource =
        TeamMemberCacheDataSourceImpl()
}
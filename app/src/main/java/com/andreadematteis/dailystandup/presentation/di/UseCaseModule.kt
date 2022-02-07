package com.andreadematteis.dailystandup.presentation.di

import com.andreadematteis.dailystandup.domain.repository.MeetingRepository
import com.andreadematteis.dailystandup.domain.usecase.CloseMeetingUseCase
import com.andreadematteis.dailystandup.domain.usecase.LoadMeetingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideLoadMeetingUseCase(meetingRepository: MeetingRepository): LoadMeetingUseCase =
        LoadMeetingUseCase(meetingRepository)

    @Singleton
    @Provides
    fun provideCloseMeetingUseCase(meetingRepository: MeetingRepository): CloseMeetingUseCase =
        CloseMeetingUseCase(meetingRepository)
}
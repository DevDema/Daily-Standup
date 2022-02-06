package com.example.dailystandup.presentation.di

import com.example.dailystandup.domain.repository.MeetingRepository
import com.example.dailystandup.domain.usecase.CloseMeetingUseCase
import com.example.dailystandup.domain.usecase.LoadMeetingUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
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
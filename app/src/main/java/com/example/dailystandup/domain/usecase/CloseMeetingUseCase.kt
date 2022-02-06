package com.example.dailystandup.domain.usecase

import com.example.dailystandup.domain.repository.MeetingRepository

class CloseMeetingUseCase(private val meetingRepository: MeetingRepository) {

    suspend fun execute() {

    }
}
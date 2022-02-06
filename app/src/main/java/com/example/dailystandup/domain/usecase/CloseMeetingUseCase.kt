package com.example.dailystandup.domain.usecase

import com.example.dailystandup.domain.repository.MeetingRepository

class CloseMeetingUseCase(val meetingRepository: MeetingRepository) {

    suspend fun execute() {

    }
}
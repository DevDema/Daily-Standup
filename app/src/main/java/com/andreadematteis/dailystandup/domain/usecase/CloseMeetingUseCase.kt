package com.andreadematteis.dailystandup.domain.usecase

import com.andreadematteis.dailystandup.domain.repository.MeetingRepository

class CloseMeetingUseCase(val meetingRepository: MeetingRepository) {

    suspend fun execute() {

    }
}
package com.example.dailystandup.domain.usecase

import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.example.dailystandup.domain.repository.MeetingRepository

class LoadMeetingUseCase(val meetingRepository: MeetingRepository) {

    suspend fun execute(meetingId: Long): MeetingAndTeamMembers =
        meetingRepository.getMeetingAndTM(meetingId)
}
package com.andreadematteis.dailystandup.domain.usecase

import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.andreadematteis.dailystandup.domain.repository.MeetingRepository

class LoadMeetingUseCase(private val meetingRepository: MeetingRepository) {

    suspend fun execute(meetingId: Long): MeetingAndTeamMembers =
        meetingRepository.getMeetingAndTM(meetingId)
}
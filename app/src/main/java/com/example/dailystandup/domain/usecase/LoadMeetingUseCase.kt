package com.example.dailystandup.domain.usecase

import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.domain.repository.MeetingRepository

class LoadMeetingUseCase(private val meetingRepository: MeetingRepository) {

    suspend fun execute(): List<TeamMember> = meetingRepository.getTeamMembers()
}
package com.andreadematteis.dailystandup.domain.usecase

import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.andreadematteis.dailystandup.domain.repository.MeetingRepository
import com.andreadematteis.dailystandup.domain.repository.MeetingTeamMemberRepository
import com.andreadematteis.dailystandup.domain.repository.TeamRepository
import com.andreadematteis.dailystandup.presentation.meeting.MeetingTeamMemberWrapper
import com.andreadematteis.dailystandup.presentation.meeting.TeamMemberStatus
import com.andreadematteis.dailystandup.presentation.meeting.TeamMemberWrapper

class LoadMeetingUseCase(
    private val meetingRepository: MeetingRepository,
    private val teamRepository: TeamRepository
) {

    suspend fun execute(meetingId: Long): MeetingTeamMemberWrapper =
        meetingRepository.getMeetingAndTM(meetingId).run {
            MeetingTeamMemberWrapper(
                meeting,
                teamMembers.mapIndexed { index, value ->
                    val team = teamRepository.getTeam(value.teamId)
                        ?: error("No team with id ${value.teamId} in for ${value.name} ${value.surname}")
                    TeamMemberWrapper(
                        value,
                        team,
                        if (index == 0) TeamMemberStatus.TALKING else TeamMemberStatus.COMING
                    )
                }
            )
        }
}
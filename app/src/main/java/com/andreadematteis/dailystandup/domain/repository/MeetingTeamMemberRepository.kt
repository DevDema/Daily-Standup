package com.andreadematteis.dailystandup.domain.repository

import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember

interface MeetingTeamMemberRepository {

    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>

    suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long
}
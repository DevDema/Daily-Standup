package com.example.dailystandup.domain.repository

import com.example.dailystandup.data.local.model.MeetingTeamMember

interface MeetingTeamMemberRepository {

    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>

    suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long
}
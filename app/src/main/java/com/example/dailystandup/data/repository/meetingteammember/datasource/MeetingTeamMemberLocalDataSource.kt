package com.example.dailystandup.data.repository.meetingteammember.datasource

import com.example.dailystandup.data.local.model.MeetingTeamMember

interface MeetingTeamMemberLocalDataSource {

    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>

    suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long
}
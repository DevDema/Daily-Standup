package com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource

import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember

interface MeetingTeamMemberLocalDataSource {

    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>

    suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long
}
package com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource

import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember

interface MeetingTeamMemberCacheDataSource {

    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>?

    suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember)
}
package com.example.dailystandup.data.repository.meetingteammember.datasource

import com.example.dailystandup.data.local.model.MeetingTeamMember

interface MeetingTeamMemberCacheDataSource {

    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>?

    suspend fun saveMeetingTeamMember(meetingAndTm: MeetingTeamMember)
}
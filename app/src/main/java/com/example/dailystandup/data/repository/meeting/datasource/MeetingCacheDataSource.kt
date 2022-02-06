package com.example.dailystandup.data.repository.meeting.datasource

import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

interface MeetingCacheDataSource {

    suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers?

    suspend fun saveMeetingAndTM(meetingAndTm: MeetingAndTeamMembers)
}
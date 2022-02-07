package com.andreadematteis.dailystandup.data.repository.meeting.datasource

import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

interface MeetingCacheDataSource {

    suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers?

    suspend fun saveMeetingAndTM(meetingAndTm: MeetingAndTeamMembers)
}
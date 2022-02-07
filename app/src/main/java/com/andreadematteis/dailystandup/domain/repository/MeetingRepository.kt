package com.andreadematteis.dailystandup.domain.repository

import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

interface MeetingRepository {

    suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers
}
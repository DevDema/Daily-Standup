package com.example.dailystandup.domain.repository

import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

interface MeetingRepository {

    suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers
}
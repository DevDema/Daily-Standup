package com.example.dailystandup.data.local.repository.meeting.datasource

import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

interface MeetingLocalDataSource {

    suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers

    suspend fun saveMeeting(meeting: Meeting): Long
}
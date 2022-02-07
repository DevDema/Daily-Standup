package com.andreadematteis.dailystandup.data.repository.meeting.datasource

import com.andreadematteis.dailystandup.data.local.model.Meeting
import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

interface MeetingLocalDataSource {

    suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers?

    suspend fun saveMeeting(meeting: Meeting): Long

    suspend fun getMeeting(meetingId: Long): Meeting?

    suspend fun getMeetings(): List<Meeting>
}
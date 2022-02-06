package com.example.dailystandup.data.repository.meeting.datasourceimpl

import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.example.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource

class MeetingLocalDataSourceImpl(private val meetingDao: MeetingDao): MeetingLocalDataSource {

    override suspend fun getMeeting(meetingId: Long): Meeting =
        meetingDao.getMeeting(meetingId)

    override suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers =
        meetingDao.getMeetingAndTeamMembers(meetingId)

    override suspend fun saveMeeting(meeting: Meeting) = meetingDao.saveMeeting(meeting)
}
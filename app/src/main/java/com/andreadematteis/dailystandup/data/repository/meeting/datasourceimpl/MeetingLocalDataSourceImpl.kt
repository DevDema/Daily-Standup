package com.andreadematteis.dailystandup.data.repository.meeting.datasourceimpl

import com.andreadematteis.dailystandup.data.local.dao.MeetingDao
import com.andreadematteis.dailystandup.data.local.model.Meeting
import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource

class MeetingLocalDataSourceImpl(private val meetingDao: MeetingDao): MeetingLocalDataSource {

    override suspend fun getMeeting(meetingId: Long): Meeting? =
        meetingDao.getMeeting(meetingId)

    override suspend fun getMeetings(): List<Meeting> =
        meetingDao.getMeetings()

    override suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers? =
        meetingDao.getMeetingAndTeamMembers(meetingId.toInt())

    override suspend fun saveMeeting(meeting: Meeting) = meetingDao.saveMeeting(meeting)
}
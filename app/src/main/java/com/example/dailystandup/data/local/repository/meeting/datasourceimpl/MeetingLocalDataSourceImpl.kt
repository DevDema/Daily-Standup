package com.example.dailystandup.data.local.repository.meeting.datasourceimpl

import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.example.dailystandup.data.local.repository.meeting.datasource.MeetingLocalDataSource

class MeetingLocalDataSourceImpl(private val meetingDao: MeetingDao): MeetingLocalDataSource {

    override suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers =
        meetingDao.getMeetingAndTeamMembers(meetingId)

}
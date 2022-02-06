package com.example.dailystandup.data.repository.meetingteammember.datasourceimpl

import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.dao.MeetingTeamMemberDao
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.MeetingTeamMember
import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.example.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberLocalDataSource

class MeetingTeamMemberLocalDataSourceImpl(private val meetingTeamMemberDao: MeetingTeamMemberDao): MeetingTeamMemberLocalDataSource {

    override suspend fun getMeetingTeamMembers(): List<MeetingTeamMember> = meetingTeamMemberDao.getMeetingTeamMembers()

    override suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember)
        = meetingTeamMemberDao.saveMeetingTeamMember(meetingTeamMember)
}
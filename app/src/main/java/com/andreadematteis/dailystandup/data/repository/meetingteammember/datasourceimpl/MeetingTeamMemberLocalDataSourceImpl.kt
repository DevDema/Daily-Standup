package com.andreadematteis.dailystandup.data.repository.meetingteammember.datasourceimpl

import com.andreadematteis.dailystandup.data.local.dao.MeetingTeamMemberDao
import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember
import com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberLocalDataSource

class MeetingTeamMemberLocalDataSourceImpl(private val meetingTeamMemberDao: MeetingTeamMemberDao): MeetingTeamMemberLocalDataSource {

    override suspend fun getMeetingTeamMembers(): List<MeetingTeamMember> = meetingTeamMemberDao.getMeetingTeamMembers()

    override suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember)
        = meetingTeamMemberDao.saveMeetingTeamMember(meetingTeamMember)
}
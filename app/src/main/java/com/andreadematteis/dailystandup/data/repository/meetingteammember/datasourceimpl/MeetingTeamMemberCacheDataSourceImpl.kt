package com.andreadematteis.dailystandup.data.repository.meetingteammember.datasourceimpl

import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember
import com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberCacheDataSource

class MeetingTeamMemberCacheDataSourceImpl : MeetingTeamMemberCacheDataSource {

    private var meetingTeamMembers = mutableListOf<MeetingTeamMember>()

    override suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>? = meetingTeamMembers.takeUnless { it.isEmpty() }

    override suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember) {
        val index = meetingTeamMembers.indexOfFirst { meetingTeamMember.id == it.id }
            .takeUnless { it == -1 }

        if (index != null) {
            meetingTeamMembers[index] = meetingTeamMember
        } else {
            meetingTeamMembers.add(meetingTeamMember)
        }
    }
}
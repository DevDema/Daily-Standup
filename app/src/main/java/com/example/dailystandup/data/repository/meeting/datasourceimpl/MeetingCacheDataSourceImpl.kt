package com.example.dailystandup.data.repository.meeting.datasourceimpl

import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.example.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource

class MeetingCacheDataSourceImpl : MeetingCacheDataSource {

    private var meetingAndTms = mutableListOf<MeetingAndTeamMembers>()

    override suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers? =
        meetingAndTms.firstOrNull {
            meetingId == it.meeting.id
        }

    override suspend fun saveMeetingAndTM(meetingAndTm: MeetingAndTeamMembers) {
        val index = meetingAndTms.indexOfFirst { meetingAndTm.meeting.id == it.meeting.id }
            .takeUnless { it == -1 }

        if (index != null) {
            meetingAndTms[index] = meetingAndTm
        } else {
            meetingAndTms.add(meetingAndTm)
        }
    }
}
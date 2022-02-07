package com.andreadematteis.dailystandup.data.repository.meeting

import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers
import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingCacheDataSource
import com.andreadematteis.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.andreadematteis.dailystandup.domain.repository.MeetingRepository

class MeetingRepositoryImpl(
    private val meetingCacheDataSource: MeetingCacheDataSource,
    private val meetingLocalDataSource: MeetingLocalDataSource
): MeetingRepository {

    override suspend fun getMeetingAndTM(meetingId: Long): MeetingAndTeamMembers =
        getMeetingAndTMFromDb(meetingId)

    private suspend fun getMeetingAndTMFromDb(meetingId: Long): MeetingAndTeamMembers = getMeetingAndTMFromCache(meetingId).run {
        return if(this == null) {
            val local = meetingLocalDataSource.getMeetingAndTM(meetingId)
                ?: error("No existing meeting with id $meetingId in Room.")
            meetingCacheDataSource.saveMeetingAndTM(local)

            local
        } else this
    }
    private suspend fun getMeetingAndTMFromCache(meetingId: Long): MeetingAndTeamMembers? =
        meetingCacheDataSource.getMeetingAndTM(meetingId)
}
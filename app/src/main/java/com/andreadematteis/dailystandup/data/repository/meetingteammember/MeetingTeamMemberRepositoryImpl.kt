package com.andreadematteis.dailystandup.data.repository.meetingteammember

import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember
import com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberCacheDataSource
import com.andreadematteis.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberLocalDataSource
import com.andreadematteis.dailystandup.domain.repository.MeetingTeamMemberRepository

class MeetingTeamMemberRepositoryImpl(
    private val meetingCacheDataSource: MeetingTeamMemberCacheDataSource,
    private val meetingLocalDataSource: MeetingTeamMemberLocalDataSource
): MeetingTeamMemberRepository {

    override suspend fun getMeetingTeamMembers(): List<MeetingTeamMember> = getMeetingTeamMembersFromDb()

    override suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long {
        val id = meetingLocalDataSource.saveMeetingTeamMember(meetingTeamMember)

        if(id > 0L) {
            meetingCacheDataSource.saveMeetingTeamMember(meetingTeamMember)
        }

        return id
    }

    private suspend fun getMeetingTeamMembersFromDb(): List<MeetingTeamMember> = getMeetingTeamMembersFromCache().run {
        return if(this == null) {
            val local = meetingLocalDataSource.getMeetingTeamMembers()

            local.forEach {
                meetingCacheDataSource.saveMeetingTeamMember(it)
            }

            local
        } else this
    }

    private suspend fun getMeetingTeamMembersFromCache(): List<MeetingTeamMember>? =
        meetingCacheDataSource.getMeetingTeamMembers()
}
package com.example.dailystandup.data.repository.meetingteammember

import com.example.dailystandup.data.local.model.MeetingTeamMember
import com.example.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberCacheDataSource
import com.example.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberLocalDataSource
import com.example.dailystandup.domain.repository.MeetingTeamMemberRepository

class MeetingTeamMemberRepositoryImpl(
    private val meetingCacheDataSource: MeetingTeamMemberCacheDataSource,
    private val meetingLocalDataSource: MeetingTeamMemberLocalDataSource
): MeetingTeamMemberRepository {

    override suspend fun getMeetingTeamMembers(): List<MeetingTeamMember> = getMeetingTeamMembersFromDb()

    override suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long {
        TODO("Not yet implemented")
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
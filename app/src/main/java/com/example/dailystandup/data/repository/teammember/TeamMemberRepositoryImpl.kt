package com.example.dailystandup.data.repository.teammember

import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.data.repository.teammember.datasource.TeamMemberCacheDataSource
import com.example.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource
import com.example.dailystandup.domain.repository.TeamMemberRepository

class TeamMemberRepositoryImpl(
    private val teamCacheDataSource: TeamMemberCacheDataSource,
    private val teamLocalDataSource: TeamMemberLocalDataSource
): TeamMemberRepository {
    override suspend fun getTeamMember(teamMemberId: Long): TeamMember? =
        getTeamMembers(teamMemberId).firstOrNull { it.id == teamMemberId }

    override suspend fun getTeamMembers(teamId: Long): List<TeamMember> = getTeamMembersFromDb(teamId)

    private suspend fun getTeamMembersFromDb(teamId: Long): List<TeamMember> = getTeamMembersFromCache(teamId).run {
        return if(this == null) {
            val local = teamLocalDataSource.getTeamMembers(teamId)

            local.forEach {
                teamCacheDataSource.saveTeamMember(it)
            }

            local
        } else this
    }
    private suspend fun getTeamMembersFromCache(teamId: Long): List<TeamMember>? =
        teamCacheDataSource.getTeamMembers(teamId)
}
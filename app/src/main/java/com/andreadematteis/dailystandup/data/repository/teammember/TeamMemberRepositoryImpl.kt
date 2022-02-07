package com.andreadematteis.dailystandup.data.repository.teammember

import com.andreadematteis.dailystandup.data.local.model.TeamMember
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberCacheDataSource
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource
import com.andreadematteis.dailystandup.domain.repository.TeamMemberRepository

class TeamMemberRepositoryImpl(
    private val teamCacheDataSource: TeamMemberCacheDataSource,
    private val teamLocalDataSource: TeamMemberLocalDataSource
): TeamMemberRepository {
    override suspend fun getTeamMember(teamMemberId: Long): TeamMember? =
        getTeamMembers(teamMemberId).firstOrNull { it.id == teamMemberId }

    override suspend fun getTeamMembers(teamId: Long): List<TeamMember> = getTeamMembersFromDb(teamId)

    override suspend fun saveTeamMember(teamMember: TeamMember): Long {
        val id = teamLocalDataSource.saveTeamMember(teamMember)

        if(id != -1L) {
            teamCacheDataSource.saveTeamMember(teamMember)
        }

        return id
    }

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
package com.example.dailystandup.data.repository.teammember.datasourceimpl

import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.data.repository.teammember.datasource.TeamMemberCacheDataSource

class TeamMemberCacheDataSourceImpl : TeamMemberCacheDataSource {

    private var teamMembers = mutableListOf<TeamMember>()

    override suspend fun getTeamMember(teamMemberId: Long): TeamMember? =
        teamMembers.firstOrNull {
            teamMemberId == it.id
        }

    override suspend fun getTeamMembers(teamId: Long): List<TeamMember>? = teamMembers.takeUnless { it.isEmpty() }

    override suspend fun saveTeamMember(teamMember: TeamMember) {
        val index = teamMembers.indexOfFirst { it.id == teamMember.id }

        if(index == -1) {
            teamMembers.add(teamMember)
        } else {
            teamMembers[index] = teamMember
        }
    }
}
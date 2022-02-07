package com.andreadematteis.dailystandup.data.repository.teammember.datasource

import com.andreadematteis.dailystandup.data.local.model.TeamMember

interface TeamMemberCacheDataSource {

    suspend fun getTeamMember(teamMemberId: Long): TeamMember?

    suspend fun getTeamMembers(teamId: Long): List<TeamMember>?

    suspend fun saveTeamMember(teamMember: TeamMember)
}
package com.andreadematteis.dailystandup.domain.repository

import com.andreadematteis.dailystandup.data.local.model.TeamMember

interface TeamMemberRepository {

    suspend fun getTeamMember(teamMemberId: Long): TeamMember?

    suspend fun getTeamMembers(teamId: Long): List<TeamMember>

    suspend fun saveTeamMember(teamMember: TeamMember): Long
}
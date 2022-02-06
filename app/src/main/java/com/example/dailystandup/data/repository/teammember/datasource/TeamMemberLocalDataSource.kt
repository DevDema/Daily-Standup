package com.example.dailystandup.data.repository.teammember.datasource

import com.example.dailystandup.data.local.model.TeamMember

interface TeamMemberLocalDataSource {

    suspend fun getTeamMember(teamMemberId: Long): TeamMember

    suspend fun getTeamMembers(teamId: Long): List<TeamMember>

    suspend fun saveTeamMember(teamMember: TeamMember): Long
}
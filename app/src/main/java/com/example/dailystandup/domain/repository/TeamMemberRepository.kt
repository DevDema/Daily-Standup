package com.example.dailystandup.domain.repository

import com.example.dailystandup.data.local.model.TeamMember

interface TeamMemberRepository {

    suspend fun getTeamMember(teamMemberId: Long): TeamMember?

    suspend fun getTeamMembers(teamId: Long): List<TeamMember>

}
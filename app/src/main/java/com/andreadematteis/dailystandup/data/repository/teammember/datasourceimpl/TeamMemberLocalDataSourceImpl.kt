package com.andreadematteis.dailystandup.data.repository.teammember.datasourceimpl

import com.andreadematteis.dailystandup.data.local.dao.TeamMemberDao
import com.andreadematteis.dailystandup.data.local.model.TeamMember
import com.andreadematteis.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource

class TeamMemberLocalDataSourceImpl(private val teamMemberDao: TeamMemberDao): TeamMemberLocalDataSource {
    override suspend fun getTeamMember(teamMemberId: Long): TeamMember = teamMemberDao.getTeamMember(teamMemberId)

    override suspend fun getTeamMembers(teamId: Long): List<TeamMember> = teamMemberDao.getTeamMembers(teamId)

    override suspend fun saveTeamMember(teamMember: TeamMember): Long =
        teamMemberDao.saveTeamMember(teamMember)
}
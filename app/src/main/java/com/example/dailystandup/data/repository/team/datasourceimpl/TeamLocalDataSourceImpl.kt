package com.example.dailystandup.data.repository.team.datasourceimpl

import com.example.dailystandup.data.local.dao.TeamDao
import com.example.dailystandup.data.local.model.Team
import com.example.dailystandup.data.repository.team.datasource.TeamLocalDataSource

class TeamLocalDataSourceImpl(private val teamDao: TeamDao): TeamLocalDataSource {
    override suspend fun getTeam(teamId: Long): Team = teamDao.getTeam(teamId)

    override suspend fun getTeams(): List<Team> = teamDao.getTeams()

    override suspend fun saveTeam(team: Team): Long =
        teamDao.saveTeam(team)


}
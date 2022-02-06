package com.example.dailystandup.data.repository.team

import com.example.dailystandup.data.local.model.Team
import com.example.dailystandup.data.repository.team.datasource.TeamCacheDataSource
import com.example.dailystandup.data.repository.team.datasource.TeamLocalDataSource
import com.example.dailystandup.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val teamCacheDataSource: TeamCacheDataSource,
    private val teamLocalDataSource: TeamLocalDataSource
): TeamRepository {
    override suspend fun getTeam(teamId: Long): Team? =
        getTeams().firstOrNull { it.id == teamId }

    override suspend fun getTeams(): List<Team> = getTeamsFromDb()

    private suspend fun getTeamsFromDb(): List<Team> = getTeamsFromCache().run {
        return if(this == null) {
            val local = teamLocalDataSource.getTeams()

            local.forEach {
                teamCacheDataSource.saveTeam(it)
            }

            local
        } else this
    }
    private suspend fun getTeamsFromCache(): List<Team>? =
        teamCacheDataSource.getTeams()
}
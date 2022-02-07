package com.andreadematteis.dailystandup.data.repository.team

import com.andreadematteis.dailystandup.data.local.model.Team
import com.andreadematteis.dailystandup.data.repository.team.datasource.TeamCacheDataSource
import com.andreadematteis.dailystandup.data.repository.team.datasource.TeamLocalDataSource
import com.andreadematteis.dailystandup.domain.repository.TeamRepository

class TeamRepositoryImpl(
    private val teamCacheDataSource: TeamCacheDataSource,
    private val teamLocalDataSource: TeamLocalDataSource
): TeamRepository {
    override suspend fun getTeam(teamId: Long): Team? =
        getTeams().firstOrNull { it.id == teamId }

    override suspend fun getTeams(): List<Team> = getTeamsFromDb()

    override suspend fun saveTeam(team: Team): Long {
        val id = teamLocalDataSource.saveTeam(team)

        if(id != -1L) {
            teamCacheDataSource.saveTeam(team)
        }

        return id
    }

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
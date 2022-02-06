package com.example.dailystandup.data.repository.team.datasourceimpl

import com.example.dailystandup.data.local.model.Team
import com.example.dailystandup.data.repository.team.datasource.TeamCacheDataSource

class TeamCacheDataSourceImpl : TeamCacheDataSource {

    private var teams = mutableListOf<Team>()

    override suspend fun getTeam(teamId: Long): Team? =
        teams.firstOrNull {
            teamId == it.id
        }

    override suspend fun getTeams(): List<Team>? = teams.takeUnless { it.isEmpty() }

    override suspend fun saveTeam(team: Team) {
        val index = teams.indexOfFirst { it.id == team.id }

        if(index == -1) {
            teams.add(team)
        } else {
            teams[index] = team
        }
    }
}
package com.andreadematteis.dailystandup.data.repository.team.datasource

import com.andreadematteis.dailystandup.data.local.model.Team

interface TeamLocalDataSource {

    suspend fun getTeam(teamId: Long): Team

    suspend fun getTeams(): List<Team>

    suspend fun saveTeam(team: Team): Long

}
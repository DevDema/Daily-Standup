package com.example.dailystandup.data.repository.team.datasource

import com.example.dailystandup.data.local.model.Team

interface TeamCacheDataSource {

    suspend fun getTeam(teamId: Long): Team?

    suspend fun getTeams(): List<Team>?

    suspend fun saveTeam(team: Team)
}
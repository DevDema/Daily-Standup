package com.example.dailystandup.data.repository.team.datasource

import com.example.dailystandup.data.local.model.Team

interface TeamLocalDataSource {

    suspend fun getTeam(teamId: Long): Team

    suspend fun getTeams(): List<Team>
}
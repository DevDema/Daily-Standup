package com.andreadematteis.dailystandup.domain.repository

import com.andreadematteis.dailystandup.data.local.model.Team

interface TeamRepository {

    suspend fun getTeam(teamId: Long): Team?

    suspend fun getTeams(): List<Team>

    suspend fun saveTeam(team: Team): Long
}
package com.example.dailystandup.domain.repository

import com.example.dailystandup.data.local.model.Team

interface TeamRepository {

    suspend fun getTeam(teamId: Long): Team?

    suspend fun getTeams(): List<Team>

}
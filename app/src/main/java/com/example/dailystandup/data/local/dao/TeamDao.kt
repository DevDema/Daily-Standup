package com.example.dailystandup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailystandup.data.local.model.Team

@Dao
interface TeamDao {

    @Query("SELECT * FROM team_table")
    suspend fun getTeams(): List<Team>

    @Query("SELECT * FROM team_table WHERE team_table.id = :teamId")
    suspend fun getTeam(teamId: Long): Team

    @Insert
    suspend fun saveTeam(team: Team): Long
}
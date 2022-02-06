package com.example.dailystandup.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dailystandup.data.local.model.TeamMember

@Dao
interface TeamMemberDao {

    @Query("SELECT * FROM team_member_table WHERE team_member_table.team_id = :teamId")
    suspend fun getTeamMembers(teamId: Long): List<TeamMember>

    suspend fun saveTeamMember(teamMember: TeamMember): Long
}
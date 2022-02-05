package com.example.dailystandup.data.local.dao

import androidx.room.Dao
import com.example.dailystandup.data.local.model.TeamMember

@Dao
interface TeamMemberDao {

    suspend fun getTeamMembers(): List<TeamMember>
}
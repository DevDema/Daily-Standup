package com.andreadematteis.dailystandup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember

@Dao
interface MeetingTeamMemberDao {

    @Query("SELECT * FROM meeting_team_member_table")
    suspend fun getMeetingTeamMembers(): List<MeetingTeamMember>

    @Insert
    suspend fun saveMeetingTeamMember(meetingTeamMember: MeetingTeamMember): Long
}
package com.example.dailystandup.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

@Dao
interface MeetingDao {

    @Query("SELECT * FROM meeting_table")
    suspend fun getMeeting(): List<Meeting>

    @Transaction
    @Query("SELECT * FROM meeting_table INNER JOIN meeting_team_member_table ON meeting_table.id = meeting_team_member_table.id WHERE id = :meetingId")
    suspend fun getMeetingAndTeamMembers(meetingId: Long): MeetingAndTeamMembers
}
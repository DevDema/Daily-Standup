package com.example.dailystandup.data.local.dao

import androidx.room.*
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

@Dao
interface MeetingDao {

    @Transaction
    @Query("SELECT * FROM meeting_table INNER JOIN meeting_team_member_table ON meeting_table.id = meeting_team_member_table.meeting_id WHERE meeting_table.id = :meetingId")
    suspend fun getMeetingAndTeamMembers(meetingId: Long): MeetingAndTeamMembers

    @Insert
    suspend fun saveMeeting(meeting: Meeting): Long

    @Query("SELECT * FROM meeting_table WHERE meeting_table.id = :meetingId")
    fun getMeeting(meetingId: Long): Meeting
}
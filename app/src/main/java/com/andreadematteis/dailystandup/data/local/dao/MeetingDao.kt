package com.andreadematteis.dailystandup.data.local.dao

import androidx.room.*
import com.andreadematteis.dailystandup.data.local.model.Meeting
import com.andreadematteis.dailystandup.data.local.model.wrapper.MeetingAndTeamMembers

@Dao
interface MeetingDao {

    @Transaction
    @Query("SELECT * FROM meeting_table INNER JOIN meeting_team_member_table ON meeting_table.id = meeting_team_member_table.meeting_id WHERE meeting_table.id = :meetingId")
    suspend fun getMeetingAndTeamMembers(meetingId: Int): MeetingAndTeamMembers?

    @Insert
    suspend fun saveMeeting(meeting: Meeting): Long

    @Query("SELECT * FROM meeting_table WHERE meeting_table.id = :meetingId")
    fun getMeeting(meetingId: Long): Meeting?

    @Query("SELECT * FROM meeting_table")
    fun getMeetings(): List<Meeting>
}
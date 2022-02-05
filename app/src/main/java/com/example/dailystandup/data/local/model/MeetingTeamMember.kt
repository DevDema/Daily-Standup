package com.example.dailystandup.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meeting_team_member_table")
class MeetingTeamMember(
    @PrimaryKey
    private val id: Long,
    @ColumnInfo(name = "meeting_id")
    private val meetingId: Long,
    @ColumnInfo(name = "team_member_id")
    private val teamMemberId: Long
)
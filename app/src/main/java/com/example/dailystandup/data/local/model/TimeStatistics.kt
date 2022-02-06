package com.example.dailystandup.data.local.model

import androidx.room.*

@Entity(
    tableName = "time_statistics_table",
    foreignKeys = [ForeignKey(
        entity = Meeting::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("meeting_id")
    ),
        ForeignKey(
            entity = TeamMember::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("team_member_id")
        )],
    indices = [ Index(value = ["team_member_id"]), Index(value = ["meeting_id"])]
)
data class TimeStatistics(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "time_millis")
    val timeMillis: Long,
    @ColumnInfo(name = "team_member_id")
    val teamMemberId: Long,
    @ColumnInfo(name = "meeting_id")
    val meetingId: Long
)
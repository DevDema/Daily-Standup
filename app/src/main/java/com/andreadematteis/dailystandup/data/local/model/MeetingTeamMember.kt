package com.andreadematteis.dailystandup.data.local.model

import androidx.room.*

@Entity(
    tableName = "meeting_team_member_table",
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
    indices = [Index(value = ["team_member_id"]), Index(value = ["meeting_id"])]
)
data class MeetingTeamMember(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "meeting_id")
    val meetingId: Long,
    @ColumnInfo(name = "team_member_id")
    val teamMemberId: Long
)
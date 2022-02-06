package com.example.dailystandup.data.local.model

import androidx.room.*

@Entity(
    tableName = "team_member_table",
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("team_id")
    )],
    indices = [Index(value = ["team_id"])]
)
data class TeamMember(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "team_id")
    val teamId: Long
)
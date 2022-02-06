package com.example.dailystandup.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_table")
data class Team(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String
)

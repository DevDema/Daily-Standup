package com.example.dailystandup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.dao.MeetingTeamMemberDao
import com.example.dailystandup.data.local.dao.TeamDao
import com.example.dailystandup.data.local.dao.TeamMemberDao
import com.example.dailystandup.data.local.model.*

@Database(
    entities = [TeamMember::class, Meeting::class, MeetingTeamMember::class, TimeStatistics::class, Team::class],
    version = 1,
    exportSchema = false
)
abstract class DailyStandupDatabase : RoomDatabase() {

    abstract fun meetingDao(): MeetingDao
    abstract fun teamMemberDao(): TeamMemberDao
    abstract fun teamDao(): TeamDao
    abstract fun meetingTeamMemberDao(): MeetingTeamMemberDao

    companion object {
        const val DATABASE_NAME = "DailyStandupDatabase"
    }
}
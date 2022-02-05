package com.example.dailystandup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailystandup.data.local.dao.MeetingDao
import com.example.dailystandup.data.local.dao.TeamMemberDao
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.MeetingTeamMember
import com.example.dailystandup.data.local.model.TeamMember

@Database(
    entities = [TeamMember::class, Meeting::class, MeetingTeamMember::class],
    version = 1,
    exportSchema = false
)
abstract class DailyStandupDatabase : RoomDatabase() {

    abstract fun meetingDao(): MeetingDao
    abstract fun teamMemberDao(): TeamMemberDao
}
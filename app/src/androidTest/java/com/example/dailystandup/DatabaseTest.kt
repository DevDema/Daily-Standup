package com.example.dailystandup

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dailystandup.data.local.DailyStandupDatabase
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.data.repository.meeting.datasourceimpl.MeetingLocalDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var applicationContext: Context
    private lateinit var database: RoomDatabase
    private lateinit var meetingDatasource: MeetingLocalDataSource

    @Before
    fun setup() {
        this.applicationContext = InstrumentationRegistry.getInstrumentation().targetContext

        val database = Room.inMemoryDatabaseBuilder(
            applicationContext, DailyStandupDatabase::class.java
        ).build()

        this.database = database
        this.meetingDatasource = MeetingLocalDataSourceImpl(database.meetingDao())
    }

    @Test
    fun testSaveMeeting() {
        val meeting = Meeting(0, "DailyStandupMock")

        runBlocking {
            meetingDatasource.saveMeeting(meeting)

        }
    }
}
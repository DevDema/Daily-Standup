package com.example.dailystandup

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.dailystandup.data.local.DailyStandupDatabase
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.MeetingTeamMember
import com.example.dailystandup.data.local.model.Team
import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.data.repository.meeting.datasource.MeetingLocalDataSource
import com.example.dailystandup.data.repository.meeting.datasourceimpl.MeetingLocalDataSourceImpl
import com.example.dailystandup.data.repository.meetingteammember.datasource.MeetingTeamMemberLocalDataSource
import com.example.dailystandup.data.repository.meetingteammember.datasourceimpl.MeetingTeamMemberLocalDataSourceImpl
import com.example.dailystandup.data.repository.team.datasource.TeamLocalDataSource
import com.example.dailystandup.data.repository.team.datasourceimpl.TeamLocalDataSourceImpl
import com.example.dailystandup.data.repository.teammember.datasource.TeamMemberLocalDataSource
import com.example.dailystandup.data.repository.teammember.datasourceimpl.TeamMemberLocalDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var applicationContext: Context
    private lateinit var database: RoomDatabase
    private lateinit var meetingDatasource: MeetingLocalDataSource
    private lateinit var teamDatasource: TeamLocalDataSource
    private lateinit var teamMemberDatasource: TeamMemberLocalDataSource
    private lateinit var meetingTeamMemberLocalDataSource: MeetingTeamMemberLocalDataSource

    @Before
    fun setup() {
        this.applicationContext = InstrumentationRegistry.getInstrumentation().targetContext

        val database = Room.inMemoryDatabaseBuilder(
            applicationContext, DailyStandupDatabase::class.java
        ).build()

        this.database = database
        this.meetingDatasource = MeetingLocalDataSourceImpl(database.meetingDao())
        this.teamDatasource = TeamLocalDataSourceImpl(database.teamDao())
        this.teamMemberDatasource = TeamMemberLocalDataSourceImpl(database.teamMemberDao())
        this.meetingTeamMemberLocalDataSource =
            MeetingTeamMemberLocalDataSourceImpl(database.meetingTeamMemberDao())
    }

    @Test
    fun testSaveMeeting() {
        val meeting = Meeting(0, "DailyStandupMock")

        runBlocking {
            val meetingId = meetingDatasource.saveMeeting(meeting)

            assertNotEquals(-1, meetingId)

            val meetingGot = meetingDatasource.getMeeting(meetingId)

            assertEquals(meeting, meetingGot)
        }
    }

    @Test
    fun testSaveTeam() {
        val team = Team(0, "mockTeam")

        runBlocking {
            val teamId = teamDatasource.saveTeam(team)

            assertNotEquals(-1, teamId)

            val teamGot = teamDatasource.getTeam(teamId)

            assertEquals(team, teamGot)
        }
    }

    @Test
    fun testSaveTeamMembers() {
        val team = Team(0, "mockTeam")

        runBlocking {
            val teamId = teamDatasource.saveTeam(team)

            assertNotEquals(-1, teamId)

            val teamMembers = listOf(
                TeamMember(0, "Test", "TestSurname", teamId),
                TeamMember(0, "Test", "TestSurname2", teamId)
            )

            teamMembers.forEach {
                val teamMemberId = teamMemberDatasource.saveTeamMember(it)

                assertNotEquals("Id for ${it.name} is -1", -1, teamMemberId)
            }
        }
    }

    @Test
    fun testSaveMeetingTeamMembers() {
        val meeting = Meeting(0, "DailyStandupMock")
        val team = Team(0, "mockTeam")

        runBlocking {
            val meetingId = meetingDatasource.saveMeeting(meeting)
            val teamId = teamDatasource.saveTeam(team)

            val teamMembers = listOf(
                TeamMember(0, "Test", "TestSurname", teamId),
                TeamMember(0, "Test", "TestSurname2", teamId)
            )

            val teamMemberIds = arrayOf(0L, 0L)

            teamMembers.forEachIndexed { index, teamMember ->
                val teamMemberId = teamMemberDatasource.saveTeamMember(teamMember)
                val id = meetingTeamMemberLocalDataSource.saveMeetingTeamMember(
                    MeetingTeamMember(
                        0,
                        meetingId,
                        teamMemberId
                    )
                )

                assertNotEquals("Id for ${teamMember.name} is -1", -1, id)
                assertNotEquals("Id for ${teamMember.name} is 0", 0, id)

                teamMemberIds[index] = teamMemberId
            }

            val meetingTeamMembers = meetingTeamMemberLocalDataSource.getMeetingTeamMembers()

            assertFalse(meetingTeamMembers.isEmpty())
            assertEquals(2, meetingTeamMembers.size)

            meetingTeamMembers.forEachIndexed { index, meetingTeamMember ->
                assertEquals(meetingId, meetingTeamMember.meetingId)
                assertEquals(teamMemberIds[index], meetingTeamMember.teamMemberId)


                val meetingAndTeamMembers = meetingDatasource.getMeetingAndTM(meetingId)

                assertEquals(meetingId, meetingAndTeamMembers.meeting.id)
                assertEquals(2, meetingAndTeamMembers.teamMembers.size)
            }
        }
    }


    @After
    fun clearDatabase() {
        database.clearAllTables()
    }
}
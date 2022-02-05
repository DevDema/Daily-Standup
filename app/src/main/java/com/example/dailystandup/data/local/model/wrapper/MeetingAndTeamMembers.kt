package com.example.dailystandup.data.local.model.wrapper

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.dailystandup.data.local.model.Meeting
import com.example.dailystandup.data.local.model.MeetingTeamMember
import com.example.dailystandup.data.local.model.TeamMember

class MeetingAndTeamMembers(
    @Embedded
    val meeting: Meeting,

    @Relation(entity = Meeting::class, parentColumn = "meeting_id", entityColumn = "team_member_id", associateBy = Junction(
        value = MeetingTeamMember::class,
        parentColumn = "id",
        entityColumn = "id"
    ))
    val teamMembers: List<TeamMember>
)
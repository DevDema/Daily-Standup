package com.andreadematteis.dailystandup.data.local.model.wrapper

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.andreadematteis.dailystandup.data.local.model.Meeting
import com.andreadematteis.dailystandup.data.local.model.MeetingTeamMember
import com.andreadematteis.dailystandup.data.local.model.TeamMember

class MeetingAndTeamMembers(
    @Embedded
    val meeting: Meeting,

    @Relation(entity = TeamMember::class, parentColumn = "id", entityColumn = "id", associateBy = Junction(
        value = MeetingTeamMember::class,
        parentColumn = "meeting_id",
        entityColumn = "team_member_id"
    ))
    val teamMembers: List<TeamMember>
)
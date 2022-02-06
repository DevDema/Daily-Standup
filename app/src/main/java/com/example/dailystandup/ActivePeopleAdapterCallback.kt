package com.example.dailystandup

import com.example.dailystandup.data.local.model.TeamMember

interface ActivePeopleAdapterCallback {

    fun onTalked(teamMember: TeamMember, elapsedTalking: Long)

    fun onSkipped(teamMember: TeamMember)

    fun onExpanded()

    fun onCollapsed()

    fun onEnd()
}
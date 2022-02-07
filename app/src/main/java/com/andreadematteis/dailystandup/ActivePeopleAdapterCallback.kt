package com.andreadematteis.dailystandup

import com.andreadematteis.dailystandup.data.local.model.TeamMember

interface ActivePeopleAdapterCallback {

    fun onTalked(teamMember: TeamMember, elapsedTalking: Long)

    fun onSkipped(teamMember: TeamMember, elapsedTalking: Long)

    fun onExpanded()

    fun onCollapsed()
}
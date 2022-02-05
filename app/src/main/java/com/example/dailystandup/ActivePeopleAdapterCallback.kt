package com.example.dailystandup

import com.example.dailystandup.data.local.model.TeamMember

interface ActivePeopleAdapterCallback {

    fun onTalked(teamMember: TeamMember, elapsedTalking: Int)

    fun onSkipped(teamMember: TeamMember)

    fun onExpanded()

    fun onCollapsed()

    fun registerSecondIntervalListener(listener: SecondIntervalListener)

    fun unregisterSecondIntervalListener(listener: SecondIntervalListener)

    fun onEnd()
}
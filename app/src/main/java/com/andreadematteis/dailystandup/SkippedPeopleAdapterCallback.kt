package com.andreadematteis.dailystandup

import com.andreadematteis.dailystandup.data.local.model.TeamMember

interface SkippedPeopleAdapterCallback {

    fun onBroughtBack(teamMember: TeamMember)
}
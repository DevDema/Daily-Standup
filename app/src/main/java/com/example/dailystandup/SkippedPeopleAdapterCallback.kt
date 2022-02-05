package com.example.dailystandup

import com.example.dailystandup.data.local.model.TeamMember

interface SkippedPeopleAdapterCallback {

    fun onBroughtBack(teamMember: TeamMember)
}
package com.example.dailystandup.presentation.meeting

import android.graphics.Bitmap
import com.example.dailystandup.data.local.model.TeamMember

class TeamMemberWrapper(
    val member: TeamMember,
    val status: TeamMemberStatus = TeamMemberStatus.COMING,
    val avatar: Bitmap? = null
)
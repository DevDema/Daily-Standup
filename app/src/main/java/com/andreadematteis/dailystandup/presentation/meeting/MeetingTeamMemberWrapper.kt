package com.andreadematteis.dailystandup.presentation.meeting

import com.andreadematteis.dailystandup.data.local.model.Meeting

data class MeetingTeamMemberWrapper(
    val meeting: Meeting,
    val memberWrapperList: List<TeamMemberWrapper>
)
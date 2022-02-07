package com.andreadematteis.dailystandup.presentation.meeting

import android.graphics.Bitmap
import com.andreadematteis.dailystandup.data.local.model.Team
import com.andreadematteis.dailystandup.data.local.model.TeamMember

data class TeamMemberWrapper(
    val member: TeamMember,
    val team: Team,
    var status: TeamMemberStatus = TeamMemberStatus.COMING,
    val avatar: Bitmap? = null,
    var timeMillis: Long = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamMemberWrapper

        if (member != other.member) return false
        if (status != other.status) return false
        if (timeMillis != other.timeMillis) return false

        return true
    }

    override fun hashCode(): Int {
        var result = member.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + timeMillis.hashCode()
        return result
    }
}
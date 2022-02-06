package com.example.dailystandup.presentation.meeting.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailystandup.R
import com.example.dailystandup.presentation.meeting.TeamMemberWrapper
import com.example.dailystandup.presentation.meeting.views.MeetingPersonTalkedView
import com.example.dailystandup.utils.DynamicAdapter

class MeetingPeopleTalkedAdapter(
    private val context: Context,
    dataSet: List<TeamMemberWrapper>
) : DynamicAdapter<TeamMemberWrapper, MeetingPeopleTalkedAdapter.ViewHolder>(dataSet.toMutableList()) {

    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonTalkedView(context))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as MeetingPersonTalkedView).run {
            val person = dataSet[position].member
            val image = dataSet[position].avatar
            val elapsedTalking = dataSet[position].timeMillis

            setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_200))
            title = "${person.name} ${person.surname}"
            subtitle = person.teamId.toString() // TODO: retrieve the team too

            setElapsedTime(elapsedTalking)
            setProfilePicture(image)
        }
    }

    override fun getItemCount(): Int = dataSet.size
}
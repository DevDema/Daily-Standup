package com.example.dailystandup.utils.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailystandup.R
import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.utils.views.MeetingPersonTalkedView

class MeetingPeopleTalkedAdapter(private val context: Context, teamMemberList: MutableList<TeamMember>) :
    RecyclerView.Adapter<MeetingPeopleTalkedAdapter.ViewHolder>() {
    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonTalkedView(context))
    private val dataSet = mutableListOf<Pair<TeamMember, Int>>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as MeetingPersonTalkedView).run {
            val person = dataSet[position].first
            val elapsedTalking = dataSet[position].second

            setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_200))
            title = "${person.name} ${person.surname}"
            subtitle = person.team

            setElapsedTime(elapsedTalking)
            setProfilePicture(person.image)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun add(teamMemberWhoTalked: TeamMember, elapsedTalking: Int) {
        dataSet.add(0, teamMemberWhoTalked to elapsedTalking)
        notifyItemInserted(0)
    }
}
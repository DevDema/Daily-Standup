package com.example.dailystandup.presentation.meeting.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailystandup.R
import com.example.dailystandup.SkippedPeopleAdapterCallback
import com.example.dailystandup.presentation.meeting.TeamMemberWrapper
import com.example.dailystandup.presentation.meeting.views.MeetingPersonSkippedView
import com.example.dailystandup.utils.DynamicAdapter

class MeetingPeopleSkippedAdapter(
    private val context: Context,
    private val skippedPeopleAdapterCallback: SkippedPeopleAdapterCallback,
    dataSet: List<TeamMemberWrapper>
) :
    DynamicAdapter<TeamMemberWrapper, MeetingPeopleSkippedAdapter.ViewHolder>(dataSet.toMutableList()) {
    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonSkippedView(context))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as MeetingPersonSkippedView).run {
            val person = dataSet[position].member
            val image = dataSet[position].avatar

            setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_200))
            title = "${person.name} ${person.surname}"
            subtitle = person.teamId.toString() // TODO: retrieve the team too

            setProfilePicture(image)
            setOnClickBringBackListener {
                dataSet.removeAt(position)
                notifyItemRemoved(position)

                skippedPeopleAdapterCallback.onBroughtBack(person)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}
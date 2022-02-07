package com.andreadematteis.dailystandup.presentation.meeting.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.dailystandup.R
import com.andreadematteis.dailystandup.SkippedPeopleAdapterCallback
import com.andreadematteis.dailystandup.presentation.meeting.TeamMemberWrapper
import com.andreadematteis.dailystandup.presentation.meeting.views.MeetingPersonSkippedView
import com.andreadematteis.dailystandup.utils.DynamicAdapter

class MeetingPeopleSkippedAdapter(
    private val context: Context,
    private val skippedPeopleAdapterCallback: SkippedPeopleAdapterCallback,
    dataSet: List<TeamMemberWrapper>
) :
    DynamicAdapter<TeamMemberWrapper, MeetingPeopleSkippedAdapter.ViewHolder>(dataSet.toMutableList()) {

    private var isEnd = false

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
            setButtonEnabled(!isEnd)

            setOnClickBringBackListener {
                dataSet.removeAt(position)

                notifyItemRemoved(position)

                for (index in position until dataSet.size) {
                    notifyItemChanged(index)
                }


                skippedPeopleAdapterCallback.onBroughtBack(person)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    fun notifyEnd(ended: Boolean) {
        isEnd = ended

        notifyItemRangeChanged(0, dataSet.size)
    }
}
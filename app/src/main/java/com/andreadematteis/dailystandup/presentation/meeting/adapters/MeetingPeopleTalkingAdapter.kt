package com.andreadematteis.dailystandup.presentation.meeting.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.dailystandup.ActivePeopleAdapterCallback
import com.andreadematteis.dailystandup.R
import com.andreadematteis.dailystandup.presentation.meeting.TeamMemberWrapper
import com.andreadematteis.dailystandup.presentation.meeting.views.MeetingPersonActiveView
import com.andreadematteis.dailystandup.presentation.meeting.views.MeetingPersonView
import com.andreadematteis.dailystandup.utils.DynamicAdapter
import com.andreadematteis.dailystandup.utils.millisToHHmmssFormat
import com.google.android.material.button.MaterialButton

class MeetingPeopleTalkingAdapter(
    private val context: Context,
    private val callback: ActivePeopleAdapterCallback,
    dataSet: MutableList<TeamMemberWrapper>
) : DynamicAdapter<TeamMemberWrapper, RecyclerView.ViewHolder>(dataSet) {

    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonView(context))
    class ViewHolderActive(context: Context) :
        RecyclerView.ViewHolder(MeetingPersonActiveView(context))

    class ViewHolderMore(context: Context) :
        RecyclerView.ViewHolder(MaterialButton(context))

    private var collapsableDataSet =
        if (dataSet.size > 6)
            dataSet.subList(0, 6).toMutableList()
        else dataSet
    private var isCollapsed = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            MeetingPersonViewType.ACTIVE.ordinal -> ViewHolderActive(context)
            MeetingPersonViewType.MORE.ordinal -> ViewHolderMore(context)
            else -> ViewHolder(context)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            position == 0 -> {
                onBindViewActive(holder.itemView as MeetingPersonActiveView, position)
            }
            holder is ViewHolderMore -> {
                onBindLastView(holder.itemView as Button)
            }
            else -> {
                onBindViewActive(holder.itemView as MeetingPersonView, position)
            }
        }
    }

    private fun onBindViewActive(view: MeetingPersonActiveView, position: Int) = view.run {
        val memberWrapper = collapsableDataSet[position]
        val person = memberWrapper.member
        val image = memberWrapper.avatar
        val elapsedSeconds = memberWrapper.timeMillis

        title = "${person.name} ${person.surname}"
        subtitle = person.teamId.toString() // TODO: retrieve the team too
        elapsedTimeText = elapsedSeconds.millisToHHmmssFormat()

        setProfilePicture(image)

        tasksButtonText = context.getString(R.string.tasks_label)
        skipButtonText = context.getString(R.string.skip_label)
        nextEndButtonText =
            if (dataSet.size == 1) context.getString(R.string.end_meeting_hyphen_label) else context.getString(
                R.string.call_next_label
            )

        setOnClickNextListener {
            callNext()

            callback.onTalked(person, elapsedSeconds)
        }

        setOnClickSkipListener {
            callNext()

            callback.onSkipped(person, elapsedSeconds)
        }

        setSkippedButtonEnabled(dataSet.size > 1)
    }

    private fun onBindViewActive(view: MeetingPersonView, position: Int) = view.run {
        val person = collapsableDataSet[position].member
        val image = collapsableDataSet[position].avatar

        title = "${person.name} ${person.surname}"
        subtitle = person.teamId.toString() // TODO: retrieve the team too

        setProfilePicture(image)
    }

    private fun onBindLastView(itemView: Button) {
        itemView.text = context.getString(R.string.view_more_label, dataSet.size - 5)
        itemView.setOnClickListener { expand() }
    }

    private fun callNext() {
        collapsableDataSet.removeAt(0)
        dataSet.removeAt(0)

        if (isCollapsed && dataSet.size > 5) {
            collapsableDataSet.add(5, dataSet[5])
            notifyItemChanged(5)
        }

        notifyItemRemoved(0)
    }

    private fun expand() {
        if (dataSet.size < 6) {
            return
        }

        isCollapsed = false

        collapsableDataSet.addAll(dataSet.subList(collapsableDataSet.size, dataSet.size))

        notifyItemChanged(5)
        for (index in 6..dataSet.size) {
            notifyItemInserted(index)
        }

        callback.onExpanded()
    }

    fun collapse() {
        if (dataSet.size < 6 || collapsableDataSet.size < 7) {
            return
        }

        isCollapsed = true

        for (index in 6 until dataSet.size) {
            collapsableDataSet.removeLast()
            notifyItemRemoved(index)
        }

        notifyItemChanged(5)
        callback.onCollapsed()
    }

    override fun handleChange(
        index: Int,
        newValue: TeamMemberWrapper,
        oldValue: TeamMemberWrapper
    ) {
        dataSet[index] = newValue
        collapsableDataSet[index] = newValue
        notifyItemChanged(index)
    }

    override fun handleInsertions(newList: List<TeamMemberWrapper>, oldSize: Int) {
        dataSet = newList.toMutableList()

        if (isCollapsed && newList.size > 6) {
            notifyItemChanged(5)
        } else {
            collapsableDataSet.addAll(newList.subList(oldSize, newList.size))
            notifyItemRangeInserted(newList.size, oldSize)
        }

        if (oldSize == 1) {
            notifyItemChanged(0)
        }
    }

    override fun handleRemovals(newList: List<TeamMemberWrapper>, oldSize: Int) {
        collapsableDataSet = newList.toMutableList()

        if (isCollapsed && collapsableDataSet.size > 5) {
            notifyItemChanged(5)
        } else {
            notifyItemRangeRemoved(oldSize, newList.size)
        }
    }

    override fun getItemCount(): Int = collapsableDataSet.size

    override fun getItemViewType(position: Int): Int =
        if (position == 0) {
            MeetingPersonViewType.ACTIVE.ordinal
        } else if (isCollapsed && collapsableDataSet.size > 5 && position == 5) {
            MeetingPersonViewType.MORE.ordinal
        } else {
            MeetingPersonViewType.STILL.ordinal
        }

    enum class MeetingPersonViewType {
        ACTIVE,
        STILL,
        MORE
    }
}
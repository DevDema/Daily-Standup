package com.example.dailystandup.utils.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.dailystandup.ActivePeopleAdapterCallback
import com.example.dailystandup.R
import com.example.dailystandup.SecondIntervalListener
import com.example.dailystandup.model.Person
import com.example.dailystandup.utils.toHHmmssFormat
import com.example.dailystandup.utils.views.MeetingPersonActiveView
import com.example.dailystandup.utils.views.MeetingPersonView
import com.google.android.material.button.MaterialButton

class MeetingPeopleTalkingAdapter(
    private val context: Context,
    private val callback: ActivePeopleAdapterCallback,
    val dataSet: MutableList<Person>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonView(context))
    class ViewHolderActive(context: Context) :
        RecyclerView.ViewHolder(MeetingPersonActiveView(context))

    class ViewHolderMore(context: Context) :
        RecyclerView.ViewHolder(MaterialButton(context))

    private val collapsableDataSet = dataSet.subList(0, 6).toMutableList()
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
                onBindView(holder.itemView as MeetingPersonActiveView, position)
            }
            holder is ViewHolderMore -> {
                onBindLastView(holder.itemView as Button)
            }
            else -> {
                onBindView(holder.itemView as MeetingPersonView, position)
            }
        }
    }

    private fun onBindLastView(itemView: Button) {
        itemView.text = "${dataSet.size - 5} more"
        itemView.setOnClickListener { expand() }
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

    private fun onBindView(view: MeetingPersonActiveView, position: Int) = view.run {
        elapsedSeconds = 0
        val listener = object: SecondIntervalListener {
            override fun onSecondPassed() {
                elapsedSeconds++
                elapsedTimeText = elapsedSeconds.toHHmmssFormat()
            }

            override fun onFinish() {
                // Do nothing
            }
        }
        val person = collapsableDataSet[position]

        title = "${person.name} ${person.surname}"
        subtitle = person.team
        setProfilePicture(person.image)

        tasksButtonText = context.getString(R.string.tasks_label)
        skipButtonText = context.getString(R.string.skip_label)
        nextEndButtonText = if(dataSet.size == 1) context.getString(R.string.end_meeting_hyphen_label) else context.getString(R.string.call_next_label)

        callback.registerSecondIntervalListener(listener)

        setOnClickNextListener {
            goNext(this, listener, position)

            if(dataSet.size == 0) {
                callback.onEnd()
            }

            callback.onTalked(person, elapsedSeconds)
        }

        setOnClickSkipListener {
            goNext(this, listener, position)
            callback.onSkipped(person)
        }

        setSkippedButtonEnabled(dataSet.size > 1)
    }

    private fun goNext(currentView: MeetingPersonActiveView, listener: SecondIntervalListener, currentPosition: Int) {
        collapsableDataSet.removeAt(currentPosition)
        dataSet.removeAt(currentPosition)

        callback.unregisterSecondIntervalListener(listener)
        if (dataSet.size > 5) {
            collapsableDataSet.add(5, dataSet[5])
            notifyItemChanged(5)
        }

        notifyItemRemoved(currentPosition)
    }

    fun add(person: Person) {
        dataSet.add(person)

        if (isCollapsed && collapsableDataSet.size > 5) {
            notifyItemChanged(5)
        } else {
            collapsableDataSet.add(person)
            notifyItemInserted(collapsableDataSet.size - 1)
        }
    }

    private fun onBindView(view: MeetingPersonView, position: Int) = view.run {
        val person = collapsableDataSet[position]

        title = "${person.name} ${person.surname}"
        subtitle = person.team
        setProfilePicture(person.image)
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
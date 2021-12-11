package com.example.dailystandup.utils.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailystandup.R
import com.example.dailystandup.SkippedPeopleAdapterCallback
import com.example.dailystandup.model.Person
import com.example.dailystandup.utils.views.MeetingPersonSkippedView

class MeetingPeopleSkippedAdapter(private val context: Context, private val skippedPeopleAdapterCallback: SkippedPeopleAdapterCallback, val dataSet: MutableList<Person>) :
    RecyclerView.Adapter<MeetingPeopleSkippedAdapter.ViewHolder>() {
    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonSkippedView(context))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(context)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as MeetingPersonSkippedView).run {
            val person = dataSet[position]

            setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_200))
            title = "${person.name} ${person.surname}"
            subtitle = person.team

            setProfilePicture(person.image)
            setOnClickBringBackListener {
                val personPosition = dataSet.indexOf(person)

                dataSet.remove(person)
                notifyItemRemoved(personPosition)

                skippedPeopleAdapterCallback.onBroughtBack(person)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    fun add(personWhoTalked: Person) {
        dataSet.add(0, personWhoTalked)
        notifyItemInserted(0)
    }
}
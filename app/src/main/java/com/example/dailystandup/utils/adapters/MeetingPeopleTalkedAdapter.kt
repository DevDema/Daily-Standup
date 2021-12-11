package com.example.dailystandup.utils.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dailystandup.R
import com.example.dailystandup.model.Person
import com.example.dailystandup.utils.views.MeetingPersonTalkedView
import com.example.dailystandup.utils.views.MeetingPersonView

class MeetingPeopleTalkedAdapter(private val context: Context, personList: MutableList<Person>) :
    RecyclerView.Adapter<MeetingPeopleTalkedAdapter.ViewHolder>() {
    class ViewHolder(context: Context) : RecyclerView.ViewHolder(MeetingPersonTalkedView(context))
    private val dataSet = mutableListOf<Pair<Person, Int>>()
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

    fun add(personWhoTalked: Person, elapsedTalking: Int) {
        dataSet.add(0, personWhoTalked to elapsedTalking)
        notifyItemInserted(0)
    }
}
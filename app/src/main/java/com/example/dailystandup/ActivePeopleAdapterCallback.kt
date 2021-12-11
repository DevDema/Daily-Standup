package com.example.dailystandup

import com.example.dailystandup.model.Person

interface ActivePeopleAdapterCallback {

    fun onTalked(person: Person, elapsedTalking: Int)

    fun onSkipped(person: Person)

    fun onExpanded()

    fun onCollapsed()

    fun registerSecondIntervalListener(listener: SecondIntervalListener)

    fun unregisterSecondIntervalListener(listener: SecondIntervalListener)

    fun onEnd()
}
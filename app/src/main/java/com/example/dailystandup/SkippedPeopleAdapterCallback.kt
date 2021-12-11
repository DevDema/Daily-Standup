package com.example.dailystandup

import com.example.dailystandup.model.Person

interface SkippedPeopleAdapterCallback {

    fun onBroughtBack(person: Person)
}
package com.example.dailystandup.model

import android.graphics.Bitmap

class Person(val name: String, val surname: String, val team: String, val image: Bitmap?) {

    constructor(name: String, surname: String, team: String) : this(name, surname, team, null)
}
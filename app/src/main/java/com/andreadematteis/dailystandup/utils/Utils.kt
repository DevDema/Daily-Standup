package com.andreadematteis.dailystandup.utils

import java.text.SimpleDateFormat
import java.util.*


fun Long.millisToHHmmssFormat(): CharSequence {
    val limitHour = 60 * 60 * 1000
    val formatter = SimpleDateFormat(
        when {
            this < limitHour -> "mm:ss"
            else -> "hh:mm:ss"
        }, Locale.ENGLISH)
    val calendar = Calendar.getInstance()

    calendar.timeInMillis = this

    return formatter.format(calendar.time)
}
package com.example.dailystandup.utils


fun Int.toHHmmssFormat(): CharSequence {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return (if(hours == 0) "" else if(hours < 10) "0$hours:"  else "$hours:") +
            "${if(minutes < 10) "0$minutes" else minutes}" +
            ":${if(seconds < 10) "0$seconds" else seconds}"
}
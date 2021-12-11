package com.example.dailystandup

import android.os.CountDownTimer

class MeetingTimer {

    private val listeners = mutableSetOf<SecondIntervalListener>()
    private val countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            listeners.forEach { it.onSecondPassed() }
        }

        override fun onFinish() {
            listeners.forEach { it.onFinish() }
        }
    }

    fun registerListener(listener: SecondIntervalListener) = listeners.add(listener)

    fun unregisterListener(listener: SecondIntervalListener) = listeners.remove(listener)

    fun start() {
        countDownTimer.start()
    }

    fun stop() {
        countDownTimer.cancel()
    }
}
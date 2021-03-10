/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.viewmodel

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.enum.CountdownStatus

class CountdownViewModel : ViewModel() {

    var remainTimeInSec = mutableStateOf(0L)
    var status = mutableStateOf(CountdownStatus.STOPPED)
    private var countDownTimer: CountDownTimer? = null

    fun isRunning() = status.value == CountdownStatus.RUNNING
    fun isPaused() = status.value == CountdownStatus.PAUSED
    fun isStopped() = status.value == CountdownStatus.STOPPED

    fun startCountdown() {
        status.value = CountdownStatus.RUNNING
        countDownTimer = object : CountDownTimer(
            remainTimeInSec.value * 1000, 1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                remainTimeInSec.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                status.value = CountdownStatus.STOPPED
            }
        }.start()
    }

    fun pauseCountdown() {
        status.value = CountdownStatus.PAUSED
        countDownTimer?.cancel()
    }

    fun resumeCountdown() {
        status.value = CountdownStatus.RESUMED
        startCountdown()
    }

    fun stopCountdown() {
        status.value = CountdownStatus.STOPPED
        countDownTimer?.cancel()
        remainTimeInSec.value = 0L
    }

    fun setRemainTime(time: String) {
        if (time.isEmpty()) {
            remainTimeInSec.value = 0L
        } else {
            remainTimeInSec.value = time.toLong()
        }
    }
}

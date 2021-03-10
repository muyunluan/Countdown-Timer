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
package com.example.androiddevchallenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.viewmodel.CountdownViewModel

@Composable
fun CountdownScreen() {
    var viewModel = viewModel(CountdownViewModel::class.java)
    var focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        var startTime by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = viewModel.remainTimeInSec.value.toString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3
            )
        }
        TextField(
            value = startTime,
            onValueChange = { startTime = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp),
            enabled = viewModel.isStopped(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.setRemainTime(startTime)
                    if (viewModel.isRunning()) {
                        viewModel.stopCountdown()
                    } else {
                        viewModel.startCountdown()
                    }
                    focusManager.clearFocus()
                }
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    viewModel.stopCountdown()
                    startTime = "0"
                }
            ) {
                Icon(imageVector = Icons.Filled.Stop, contentDescription = "Stop")
            }
            Button(
                onClick = {
                    when {
                        viewModel.isPaused() -> {
                            viewModel.resumeCountdown()
                        }
                        viewModel.isRunning() -> {
                            viewModel.pauseCountdown()
                        }
                        viewModel.isStopped() -> {
                            viewModel.startCountdown()
                        }
                    }
                }
            ) {
                if (!viewModel.isRunning()) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Start/Resume")
                } else {
                    Icon(imageVector = Icons.Filled.Pause, contentDescription = "Pause")
                }
            }
        }
    }
}

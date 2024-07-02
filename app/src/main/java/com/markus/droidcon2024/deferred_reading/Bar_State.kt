package com.markus.droidcon2024.deferred_reading

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Preview
@Composable
fun Bar1() {
    val state = remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        delay(2_000)
        while (true) {
            delay(500)
            state.intValue++
        }
    }

    Bar2(state)
}

@Composable
fun Bar2(state: IntState) = Bar3(state)

@Composable
fun Bar3(state: IntState) = Bar4(state)

@Composable
fun Bar4(state: IntState) = Bar5(state)

@Composable
fun Bar5(state: IntState) {
    Text(text = "State = ${state.intValue}")
}
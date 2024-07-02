package com.markus.droidcon2024.deferred_reading

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Preview
@Composable
fun Baz1() {
    val state = remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        delay(2_000)
        while (true) {
            delay(500)
            state.intValue++
        }
    }

    Baz2 { state.intValue }
}

@Composable
fun Baz2(state: () -> Int) = Baz3(state)

@Composable
fun Baz3(state: () -> Int) = Baz4(state)

@Composable
fun Baz4(state: () -> Int) = Baz5(state)

@Composable
fun Baz5(state: () -> Int) {
    Text(text = "State = ${state.invoke()}")
}
package com.markus.droidcon2024.deferred_reading

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview
@Composable
fun Foo1() {
    var state by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        delay(2_000)
        while (true) {
            delay(500)
            state++
        }
    }

    Foo2(state)
}

@Composable
fun Foo2(state: Int) = Foo3(state)

@Composable
fun Foo3(state: Int) = Foo4(state)

@Composable
fun Foo4(state: Int) = Foo5(state = state)

@Composable
fun Foo5(state: Int) {
    Text(text = "State = $state", fontSize = 48.sp)
}
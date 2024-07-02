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
fun Qux1() {
    val state = remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        delay(2_000)
        while (true) {
            delay(500)
            state.intValue++
        }
    }

    Qux2 {
        Qux3 {
            Qux4 {
                Qux5(state = state.intValue)
            }
        }
    }
}

@Composable
fun Qux2(content: @Composable () -> Unit) {
    content.invoke()
}

@Composable
fun Qux3(content: @Composable () -> Unit) {
    content.invoke()
}

@Composable
fun Qux4(content: @Composable () -> Unit) {
    content.invoke()
}

@Composable
fun Qux5(state: Int) {
    Text(text = "State = $state")
}
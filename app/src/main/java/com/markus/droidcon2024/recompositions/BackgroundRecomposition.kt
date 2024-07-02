package com.markus.droidcon2024.recompositions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import com.markus.droidcon2024.recompositions.modifier.background
import com.markus.droidcon2024.recompositions.components.RadioButtons
import com.markus.droidcon2024.recompositions.model.ColorState.RED
import com.markus.droidcon2024.recompositions.model.color

@Preview
@Composable
fun BackgroundScreen1() {
    val state = remember { mutableStateOf(RED) }
    val colorBackground = animateColorAsState(
        targetValue = state.value.color,
        animationSpec = tween(durationMillis = 1000)
    )

    MaxSizeColumn(
        modifier = Modifier
            .background(colorBackground.value),
    ) {
        RadioButtons(
            color = state.value,
            onColorChange = { state.value = it },
        )
    }
}

@Preview
@Composable
fun BackgroundScreen3() {
    val state = remember { mutableStateOf(RED) }
    val colorBackground = animateColorAsState(
        targetValue = state.value.color,
        animationSpec = tween(durationMillis = 1000)
    )

    MaxSizeColumn(
        modifier = Modifier
            .background(colorBackground),
    ) {
        RadioButtons(
            color = state.value,
            onColorChange = { state.value = it },
        )
    }
}

@Preview
@Composable
fun BackgroundScreen2() {
    val state = remember { mutableStateOf(RED) }
    val colorBackground = animateColorAsState(
        targetValue = state.value.color,
        animationSpec = tween(durationMillis = 1000)
    )

    MaxSizeColumn(
        modifier = Modifier
            .background { colorBackground.value },
    ) {
        RadioButtons(
            color = state.value,
            onColorChange = { state.value = it },
        )
    }
}

@Preview
@Composable
fun BackgroundScreenDrawBehind() {
    val state = remember { mutableStateOf(RED) }
    val colorBackground = animateColorAsState(
        targetValue = state.value.color,
        animationSpec = tween(durationMillis = 1000)
    )

    MaxSizeColumn(
        modifier = Modifier
            .drawBehind {
                drawRect(colorBackground.value)
            }
    ) {
        RadioButtons(
            color = state.value,
            onColorChange = { state.value = it },
        )
    }
}

@Composable
fun MaxSizeColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        content = content,
    )

}
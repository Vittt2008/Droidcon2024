package com.markus.droidcon2024.recompositions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun AlphaScreen1() {
    val visible = remember { mutableStateOf(true) }
    val alpha = animateFloatAsState(
        targetValue = if (visible.value) 1f else 0.5f,
        animationSpec = tween(1_000),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier
                .size(120.dp, 48.dp)
                .alpha(alpha.value),
            onClick = { visible.value = !visible.value },
            elevation = null,
        ) {
            Text("Click")
        }
    }
}

@Preview
@Composable
fun AlphaScreen2() {
    val visible = remember { mutableStateOf(true) }
    val alpha = animateFloatAsState(
        targetValue = if (visible.value) 1f else 0.5f,
        animationSpec = tween(1_000),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier
                .size(120.dp, 48.dp)
                .graphicsLayer { this.alpha = alpha.value },
            onClick = { visible.value = !visible.value },
            elevation = null,
        ) {
            Text("Click")
        }
    }
}

@Preview
@Composable
fun AlphaScreen3() {
    val visible = remember { mutableStateOf(true) }
    val alpha = animateFloatAsState(
        targetValue = if (visible.value) 1f else 0.5f,
        animationSpec = tween(1_000),
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(
            modifier = Modifier
                .size(120.dp, 48.dp)
                .alphaLayer(alpha.value),
            onClick = { visible.value = !visible.value },
            elevation = null,
        ) {
            Text("Click")
        }
    }
}

fun Modifier.alphaLayer(alpha: Float): Modifier {
    return this.graphicsLayer { this.alpha = alpha }
}

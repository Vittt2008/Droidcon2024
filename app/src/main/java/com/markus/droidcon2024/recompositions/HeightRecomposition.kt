package com.markus.droidcon2024.recompositions

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.markus.droidcon2024.recompositions.components.CenterBox
import com.example.flo.compose.recomposition.modifier.height

@Preview
@Composable
fun HeightScreen1() {
    val expanded = remember { mutableStateOf(false) }
    val text = if (expanded.value) "Expanded" else "Collapsed"
    val height = if (expanded.value) 200.dp else 100.dp

    CenterBox {
        Button(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.height(height),
            elevation = null,
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun HeightScreen2() {
    val expanded = remember { mutableStateOf(false) }
    val text = if (expanded.value) "Expanded" else "Collapsed"
    val height = animateDpAsState(if (expanded.value) 200.dp else 100.dp)

    CenterBox {
        Button(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.height(height.value),
            elevation = null,
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun HeightScreen3() {
    val expanded = remember { mutableStateOf(false) }
    val text = if (expanded.value) "Expanded" else "Collapsed"
    val height = animateDpAsState(if (expanded.value) 200.dp else 100.dp)

    CenterBox {
        Button(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.height(height),
            elevation = null,
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun HeightScreen4() {
    val expanded = remember { mutableStateOf(false) }
    val text = if (expanded.value) "Expanded" else "Collapsed"
    val height = animateDpAsState(if (expanded.value) 200.dp else 100.dp)

    CenterBox {
        Button(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.height { height.value },
            elevation = null,
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun HeightScreenLayout() {
    val expanded = remember { mutableStateOf(false) }
    val text = if (expanded.value) "Expanded" else "Collapsed"
    val height = animateDpAsState(if (expanded.value) 200.dp else 100.dp)

    CenterBox {
        Button(
            onClick = { expanded.value = !expanded.value },
            modifier = Modifier.layout { measurable, constraints ->
                val newHeight = height.value.roundToPx()
                val newConstraints = constraints.copy(
                    minHeight = newHeight, maxHeight = newHeight,
                )
                val placeable = measurable.measure(newConstraints)
                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(IntOffset.Zero)
                }
            },
            elevation = null,
        ) {
            Text(text = text)
        }
    }
}
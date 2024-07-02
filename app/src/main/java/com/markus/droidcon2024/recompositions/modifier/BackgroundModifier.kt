package com.markus.droidcon2024.recompositions.modifier

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.debugInspectorInfo
import com.markus.droidcon2024.recompositions.modifier.modifier.LambdaBackgroundModifier
import com.markus.droidcon2024.recompositions.modifier.modifier.StateBackgroundModifier

fun Modifier.background(
    color: State<Color>,
    shape: Shape = RectangleShape,
) = this.then(
    StateBackgroundModifier(
        color = color,
        shape = shape,
        inspectorInfo = debugInspectorInfo {
            name = "background"
            value = color
            properties["color"] = color
            properties["shape"] = shape
        }
    )
)

fun Modifier.background(
    brush: State<Brush>,
    shape: Shape = RectangleShape,
    /*@FloatRange(from = 0.0, to = 1.0)*/
    alpha: Float = 1.0f,
) = this.then(
    StateBackgroundModifier(
        brush = brush,
        alpha = alpha,
        shape = shape,
        inspectorInfo = debugInspectorInfo {
            name = "background"
            properties["alpha"] = alpha
            properties["brush"] = brush
            properties["shape"] = shape
        }
    )
)

fun Modifier.background(
    shape: Shape = RectangleShape,
    color: () -> Color,
) = this.then(
    LambdaBackgroundModifier(
        color = color,
        shape = shape,
        inspectorInfo = debugInspectorInfo {
            name = "background"
            value = color
            properties["color"] = color
            properties["shape"] = shape
        }
    )
)

fun Modifier.background(
    brush: () -> Brush,
    shape: Shape = RectangleShape,
    /*@FloatRange(from = 0.0, to = 1.0)*/
    alpha: Float = 1.0f,
) = this.then(
    LambdaBackgroundModifier(
        brush = brush,
        alpha = alpha,
        shape = shape,
        inspectorInfo = debugInspectorInfo {
            name = "background"
            properties["alpha"] = alpha
            properties["brush"] = brush
            properties["shape"] = shape
        }
    )
)
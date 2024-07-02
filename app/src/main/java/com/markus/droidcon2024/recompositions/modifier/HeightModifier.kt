package com.example.flo.compose.recomposition.modifier

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import com.markus.droidcon2024.recompositions.modifier.element.LambdaSizeElement
import com.markus.droidcon2024.recompositions.modifier.element.StateSizeElement

@Stable
fun Modifier.height(height: State<Dp>) = this.then(
    StateSizeElement(
        minHeight = height,
        maxHeight = height,
        enforceIncoming = true,
        inspectorInfo = debugInspectorInfo {
            name = "height"
            value = height
        }
    )
)

@Stable
fun Modifier.height(height: () -> Dp) = this.then(
    LambdaSizeElement(
        minHeight = height,
        maxHeight = height,
        enforceIncoming = true,
        inspectorInfo = debugInspectorInfo {
            name = "height"
            value = height
        }
    )
)
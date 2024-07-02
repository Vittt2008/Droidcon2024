package com.markus.droidcon2024.recompositions.model

import androidx.compose.ui.graphics.Color
import com.markus.droidcon2024.recompositions.model.ColorState.BLUE
import com.markus.droidcon2024.recompositions.model.ColorState.PURPLE
import com.markus.droidcon2024.recompositions.model.ColorState.RED

enum class ColorState {
    BLUE, PURPLE, RED
}

val ColorState.color: Color
    get() = when (this) {
        BLUE -> blueColor
        PURPLE -> purpleColor
        RED -> redColor
    }

private val blueColor = Color(0xFFE7F3F3)
private val purpleColor = Color(0xFFEFEFF7)
private val redColor = Color(0xFFF7E1E0)

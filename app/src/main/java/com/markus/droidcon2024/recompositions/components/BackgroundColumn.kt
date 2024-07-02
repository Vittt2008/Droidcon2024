package com.markus.droidcon2024.recompositions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.markus.droidcon2024.recompositions.modifier.background

@Composable
fun BackgroundColumn(colorBackground: Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBackground),
        content = content,
    )
}

@Composable
fun BackgroundColumn(colorBackground: State<Color>, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBackground),
        content = content,
    )
}

@Composable
fun BackgroundColumn(colorBackground: () -> Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBackground),
        content = content,
    )
}
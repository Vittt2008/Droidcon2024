package com.markus.droidcon2024.recompositions.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.markus.droidcon2024.recompositions.model.ColorState
import com.markus.droidcon2024.recompositions.model.ColorState.BLUE
import com.markus.droidcon2024.recompositions.model.ColorState.PURPLE
import com.markus.droidcon2024.recompositions.model.ColorState.RED

@Composable
fun RadioButtons(color: ColorState, onColorChange: (ColorState) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = color == BLUE, onClick = { onColorChange.invoke(BLUE) })
        Text(text = "Blue", fontSize = 18.sp)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = color == PURPLE, onClick = { onColorChange.invoke(PURPLE) })
        Text(text = "Purple", fontSize = 18.sp)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = color == RED, onClick = { onColorChange.invoke(RED) })
        Text(text = "Red", fontSize = 18.sp)
    }
}
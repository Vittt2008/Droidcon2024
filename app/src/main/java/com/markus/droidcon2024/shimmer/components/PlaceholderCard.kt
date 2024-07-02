package com.markus.droidcon2024.shimmer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
internal fun PlaceholderCard() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        PlaceholderText()
        PlaceholderText(modifier = Modifier.padding(top = 8.dp))
        PlaceholderItems(modifier = Modifier.padding(top = 16.dp))
    }
}

@Preview
@Composable
private fun PlaceholderItems(modifier: Modifier = Modifier, count: Int = 3) {
    Column(modifier = modifier) {
        repeat(times = count) {
            PlaceholderItem()
        }
    }
}

@Preview
@Composable
private fun PlaceholderItem(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Spacer(
            modifier = Modifier
                .size(48.dp)
                .background(color = Color.LightGray, shape = CircleShape),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        ) {
            PlaceholderText()
            PlaceholderText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.5f)
            )
        }
    }
}

@Preview
@Composable
private fun PlaceholderText(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(12.dp),
            ),
    )
}
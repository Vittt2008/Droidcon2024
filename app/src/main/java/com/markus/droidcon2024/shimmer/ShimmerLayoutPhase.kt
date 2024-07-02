package com.markus.droidcon2024.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.markus.droidcon2024.shimmer.components.PlaceholderCard
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.AnimationDelay
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.AnimationDuration
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.DefaultWidth
import com.markus.droidcon2024.shimmer.components.rememberShimmerProgress
import kotlin.math.roundToInt

@Composable
private fun Shimmer(
    modifier: Modifier = Modifier,
    shimmerAnimationDuration: Int = AnimationDuration,
    shimmerAnimationDurationDelay: Int = AnimationDelay,
    shimmerWidth: Dp = DefaultWidth,
    centerGradientColor: Color = Color.Magenta,
    startEndGradientColor: Color = Color.Green,
    density: Density = LocalDensity.current,
    content: @Composable () -> Unit,
) {
    val brush = remember {
        horizontalGradient(
            colors = listOf(
                startEndGradientColor,
                centerGradientColor,
                startEndGradientColor,
            ),
            endX = with(density) { shimmerWidth.toPx() },
        )
    }
    val progress = rememberShimmerProgress(
        animationDuration = shimmerAnimationDuration,
        delayDuration = shimmerAnimationDurationDelay,
    )

    val width = remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .then(modifier)
            .onSizeChanged { size -> width.intValue = size.width }
    ) {
        content()
        Spacer(
            modifier = Modifier
                .offset { IntOffset(x = lerp(-shimmerWidth.roundToPx(), width.intValue, progress.value), y = 0) }
                .width(shimmerWidth)
                .fillMaxHeight()
                .background(brush)
        )
    }
}

@Preview
@Composable
private fun ShimmerPreview() {
    Shimmer(modifier = Modifier.fillMaxSize()) {
        Column {
            PlaceholderCard()
            PlaceholderCard()
        }
    }
}

private fun lerp(start: Int, stop: Int, fraction: Float): Int {
    return start + ((stop - start) * fraction.toDouble()).roundToInt()
}
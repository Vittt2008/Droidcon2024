package com.markus.droidcon2024.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.google.android.material.math.MathUtils.lerp
import com.markus.droidcon2024.shimmer.components.PlaceholderCard
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.AnimationDelay
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.AnimationDuration
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.DefaultWidth
import com.markus.droidcon2024.shimmer.components.rememberShimmerProgress

@Composable
private fun Shimmer(
    modifier: Modifier = Modifier,
    shimmerAnimationDuration: Int = AnimationDuration,
    shimmerAnimationDurationDelay: Int = AnimationDelay,
    shimmerWidth: Dp = DefaultWidth,
    centerGradientColor: Color = Color.Magenta,
    startEndGradientColor: Color = Color.Green,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .shimmer(
                shimmerAnimationDuration = shimmerAnimationDuration,
                shimmerAnimationDurationDelay = shimmerAnimationDurationDelay,
                shimmerWidth = shimmerWidth,
                centerGradientColor = centerGradientColor,
                startEndGradientColor = startEndGradientColor,
            )
            .then(modifier)
    ) {
        content()
    }
}

private fun Modifier.shimmer(
    shimmerAnimationDuration: Int = AnimationDuration,
    shimmerAnimationDurationDelay: Int = AnimationDelay,
    shimmerWidth: Dp = DefaultWidth,
    centerGradientColor: Color = Color.Magenta,
    startEndGradientColor: Color = Color.Green,
): Modifier = composed {

    val progress = rememberShimmerProgress(
        animationDuration = shimmerAnimationDuration,
        delayDuration = shimmerAnimationDurationDelay,
    )

    clipToBounds()
        .drawWithCache {
            val width = shimmerWidth.toPx()
            val brush = horizontalGradient(
                colors = listOf(
                    startEndGradientColor,
                    centerGradientColor,
                    startEndGradientColor,
                ),
                startX = 0F,
                endX = width,
            )
            val brushSize = Size(width = width, height = size.height)

            onDrawWithContent {
                drawContent()

                val startX = lerp(-width, size.width, progress.value)
                translate(left = startX) {
                    drawRect(
                        brush = brush,
                        size = brushSize,
                    )
                }
            }
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
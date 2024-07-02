package com.markus.droidcon2024.shimmer.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode.Restart
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.unit.dp
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.AnimationDelay
import com.markus.droidcon2024.shimmer.components.ShimmerDefaults.AnimationDuration

internal object ShimmerDefaults {

    const val AnimationDuration = 1000
    const val AnimationDelay = 1000
    val DefaultWidth = 70.dp
}

@Composable
fun rememberShimmerProgress(
    animationDuration: Int = AnimationDuration,
    delayDuration: Int = AnimationDelay,
): State<Float> {
    return rememberInfiniteTransition()
        .animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing,
                    delayMillis = delayDuration,
                ),
                repeatMode = Restart,
                initialStartOffset = StartOffset(
                    offsetMillis = delayDuration,
                    offsetType = StartOffsetType.FastForward,
                )
            )
        )
}
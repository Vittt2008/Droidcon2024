package com.markus.droidcon2024.recompositions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec.RawRes
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.markus.droidcon2024.R
import com.markus.droidcon2024.recompositions.components.CenterBox

@Composable
fun Lottie1(
    modifier: Modifier = Modifier,
) {
    CenterBox {
        val composition = rememberLottieComposition(RawRes(R.raw.lottie_animation))
        val progress = animateLottieCompositionAsState(
            composition = composition.value,
            iterations = Int.MAX_VALUE,
        )

        LottieAnimation(
            composition = composition.value,
            progress = progress.value,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
fun Lottie1Preview() {
    Lottie1()
}

@Composable
fun Lottie2(
    modifier: Modifier = Modifier,
) {
    CenterBox {
        val composition = rememberLottieComposition(RawRes(R.raw.lottie_animation))
        val progress = animateLottieCompositionAsState(
            composition = composition.value,
            iterations = Int.MAX_VALUE,
        )

        LottieAnimation(
            composition = composition.value,
            progress = { progress.value },
            modifier = modifier,
        )
    }
}

@Preview
@Composable
fun Lottie2Preview() {
    Lottie2()
}
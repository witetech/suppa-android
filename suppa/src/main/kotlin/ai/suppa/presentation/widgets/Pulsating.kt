package ai.suppa.presentation.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun Pulsating(
    pulseFraction: Float = 1.2f,
    content: @Composable () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = pulseFraction,
        animationSpec =
            infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "AnimateFloat",
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}

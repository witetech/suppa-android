package ai.suppa.presentation.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*

enum class AnimationType {
    Bounce,
    LazyBounce,
    Fade,
}

private const val NumIndicators = 3
private const val IndicatorSize = 12
private const val BounceAnimationDurationMillis = 300
private const val FadeAnimationDurationMillis = 600
private val MarginHalf = 4.dp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    indicatorSpacing: Dp = MarginHalf,
    animationType: AnimationType = AnimationType.Bounce,
) {
    val state = rememberLoadingIndicatorState(true, animationType)
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(NumIndicators) { index ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(IndicatorSize.dp)
                    .aspectRatio(1f)
                    .then(
                        when (animationType) {
                            AnimationType.Bounce,
                            AnimationType.LazyBounce,
                            -> Modifier.offset(
                                y = state[index].coerceAtMost(
                                    IndicatorSize / 2f,
                                ).dp,
                            )

                            AnimationType.Fade -> Modifier.graphicsLayer { alpha = state[index] }
                        },
                    ),
                color = color,
            )
        }
    }
}

@Composable
private fun LoadingDot(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = color),
    )
}

@Composable
fun rememberLoadingIndicatorState(
    animating: Boolean,
    animationType: AnimationType,
): LoadingIndicatorState {
    val state = remember {
        LoadingIndicatorStateImpl()
    }
    LaunchedEffect(key1 = Unit) {
        if (animating) {
            state.start(animationType, this)
        }
    }
    return state
}

private val AnimationType.animationSpec: DurationBasedAnimationSpec<Float>
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.Fade,
        -> tween(durationMillis = animationDuration)

        AnimationType.LazyBounce -> keyframes {
            durationMillis = animationDuration
            initialValue at 0
            0f at animationDuration / 4
            targetValue / 2f at animationDuration / 2
            targetValue / 2f at animationDuration
        }
    }

private val AnimationType.animationDuration: Int
    get() = when (this) {
        AnimationType.Bounce,
        AnimationType.LazyBounce,
        -> BounceAnimationDurationMillis

        AnimationType.Fade -> FadeAnimationDurationMillis
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NumIndicators

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> IndicatorSize / 2f
        AnimationType.LazyBounce -> -IndicatorSize / 2f
        AnimationType.Fade -> 1f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -IndicatorSize / 2f
        AnimationType.LazyBounce -> IndicatorSize / 2f
        AnimationType.Fade -> .2f
    }

@Stable
interface LoadingIndicatorState {
    operator fun get(index: Int): Float

    fun start(
        animationType: AnimationType,
        scope: CoroutineScope,
    )
}

class LoadingIndicatorStateImpl : LoadingIndicatorState {
    private val animatedValues = List(NumIndicators) { mutableStateOf(0f) }

    override fun get(index: Int): Float = animatedValues[index].value

    override fun start(
        animationType: AnimationType,
        scope: CoroutineScope,
    ) {
        repeat(NumIndicators) { index ->
            scope.launch {
                animate(
                    initialValue = animationType.initialValue,
                    targetValue = animationType.targetValue,
                    animationSpec = infiniteRepeatable(
                        animation = animationType.animationSpec,
                        repeatMode = RepeatMode.Reverse,
                        initialStartOffset = StartOffset(animationType.animationDelay * index),
                    ),
                ) { value, _ -> animatedValues[index].value = value }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LoadingIndicatorStateImpl

        if (animatedValues != other.animatedValues) return false

        return true
    }

    override fun hashCode(): Int {
        return animatedValues.hashCode()
    }
}

package ai.suppa.presentation.widgets

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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

private const val NUM_INDICATORS = 3
private const val INDICATOR_SIZE = 12
private const val BOUNCE_ANIM_DURATION = 300
private const val FADE_ANIM_DURATION = 600
private val MARGIN_HALF = 4.dp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.DarkGray,
    indicatorSpacing: Dp = MARGIN_HALF,
    animationType: AnimationType = AnimationType.Bounce,
) {
    val state = rememberLoadingIndicatorState(true, animationType)
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(NUM_INDICATORS) { index ->
            LoadingDot(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(INDICATOR_SIZE.dp)
                    .aspectRatio(1f)
                    .then(
                        when (animationType) {
                            AnimationType.Bounce,
                            AnimationType.LazyBounce,
                            -> Modifier.offset(
                                y = state[index].coerceAtMost(
                                    INDICATOR_SIZE / 2f,
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
        -> BOUNCE_ANIM_DURATION

        AnimationType.Fade -> FADE_ANIM_DURATION
    }

private val AnimationType.animationDelay: Int
    get() = animationDuration / NUM_INDICATORS

private val AnimationType.initialValue: Float
    get() = when (this) {
        AnimationType.Bounce -> INDICATOR_SIZE / 2f
        AnimationType.LazyBounce -> -INDICATOR_SIZE / 2f
        AnimationType.Fade -> 1f
    }

private val AnimationType.targetValue: Float
    get() = when (this) {
        AnimationType.Bounce -> -INDICATOR_SIZE / 2f
        AnimationType.LazyBounce -> INDICATOR_SIZE / 2f
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
    private val animatedValues = List(NUM_INDICATORS) { mutableStateOf(0f) }

    override fun get(index: Int): Float = animatedValues[index].value

    override fun start(
        animationType: AnimationType,
        scope: CoroutineScope,
    ) {
        repeat(NUM_INDICATORS) { index ->
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

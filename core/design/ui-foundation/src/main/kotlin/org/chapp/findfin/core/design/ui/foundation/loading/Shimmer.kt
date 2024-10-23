package org.chapp.findfin.core.design.ui.foundation.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Applies a shimmer effect to the given [Modifier].
 *
 * @param widthOfShadowBrush The width of the shadow brush used for the shimmer effect. Default is 500.
 * @param angleOfAxisY The angle of the axis Y for the shimmer effect. Default is 270f.
 * @param durationMillis The duration of the shimmer animation in milliseconds. Default is 1000.
 * @return A [Modifier] with the shimmer effect applied.
 *
 * Example usage:
 * ```
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .shimmer()
 * )
 * ```
 */
fun Modifier.shimmer(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
) = this.composed {
    val shimmerColors = ShimmerAnimationData(MaterialTheme.colorScheme.outlineVariant).getColours()

    val transition = rememberInfiniteTransition(label = "Shimmer transition")

    val translateAnimation =
        transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec =
                infiniteRepeatable(
                    animation =
                        tween(
                            durationMillis = durationMillis,
                            easing = LinearEasing,
                        ),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "Shimmer translate animation",
        )

    background(
        brush =
            Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
    )
}

private data class ShimmerAnimationData(
    private val color: Color,
) {
    fun getColours(): List<Color> {
        return listOf(
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 1.0f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f),
        )
    }
}

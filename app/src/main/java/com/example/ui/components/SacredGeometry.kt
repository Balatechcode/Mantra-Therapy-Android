package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.WarmGold
import kotlin.math.cos
import kotlin.math.sin

/**
 * Sacred Concentric Geometry Watermark used in Backgrounds
 */
@Composable
fun SacredMandalaWatermark(
    modifier: Modifier = Modifier,
    color: Color = SandalwoodPrimary,
    alpha: Float = 0.07f
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = size.minDimension / 2f

        // Concentric guide circles
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = maxRadius * 0.95f,
            center = center,
            style = Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 8f), 0f))
        )
        drawCircle(
            color = color.copy(alpha = alpha * 1.2f),
            radius = maxRadius * 0.85f,
            center = center,
            style = Stroke(width = 1.2f)
        )
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = maxRadius * 0.68f,
            center = center,
            style = Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
        )
        drawCircle(
            color = color.copy(alpha = alpha * 1.3f),
            radius = maxRadius * 0.52f,
            center = center,
            style = Stroke(width = 1.5f)
        )
        drawCircle(
            color = color.copy(alpha = alpha),
            radius = maxRadius * 0.35f,
            center = center,
            style = Stroke(width = 1f)
        )
        drawCircle(
            color = color.copy(alpha = alpha * 1.5f),
            radius = maxRadius * 0.18f,
            center = center,
            style = Stroke(width = 1.5f)
        )

        // Sacred Hexagram / Yantra Star lines
        for (i in 0 until 12) {
            val angle = Math.toRadians((i * 30.0)).toFloat()
            val startX = center.x + cos(angle) * (maxRadius * 0.2f)
            val startY = center.y + sin(angle) * (maxRadius * 0.2f)
            val endX = center.x + cos(angle) * (maxRadius * 0.95f)
            val endY = center.y + sin(angle) * (maxRadius * 0.95f)
            drawLine(
                color = color.copy(alpha = alpha * 0.8f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 1f
            )
        }

        // Intersecting Triangles
        val triPath1 = Path().apply {
            val r = maxRadius * 0.85f
            moveTo(center.x, center.y - r)
            lineTo(center.x + r * 0.866f, center.y + r * 0.5f)
            lineTo(center.x - r * 0.866f, center.y + r * 0.5f)
            close()
        }
        drawPath(triPath1, color = color.copy(alpha = alpha * 0.9f), style = Stroke(width = 1.2f))

        val triPath2 = Path().apply {
            val r = maxRadius * 0.85f
            moveTo(center.x, center.y + r)
            lineTo(center.x + r * 0.866f, center.y - r * 0.5f)
            lineTo(center.x - r * 0.866f, center.y - r * 0.5f)
            close()
        }
        drawPath(triPath2, color = color.copy(alpha = alpha * 0.9f), style = Stroke(width = 1.2f))
    }
}

/**
 * Animated Sacred Emblem for Splash & Hero Canvas
 */
@Composable
fun AnimatedSacredYantraEmblem(
    modifier: Modifier = Modifier,
    sizeDp: Dp = 144.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "yantra")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(48000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier.size(sizeDp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(sizeDp)) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f

            // Outer dashed circle
            drawCircle(
                color = WarmGold.copy(alpha = 0.6f),
                radius = radius * 0.92f,
                center = center,
                style = Stroke(width = 1.4f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 6f), 0f))
            )

            // Outer solid circle
            drawCircle(
                color = SandalwoodContainer,
                radius = radius * 0.80f,
                center = center,
                style = Stroke(width = 1.4f)
            )

            rotate(rotation, pivot = center) {
                // Upward Triangle (Gold)
                val upTri = Path().apply {
                    val r = radius * 0.68f
                    moveTo(center.x, center.y - r)
                    lineTo(center.x + r * 0.866f, center.y + r * 0.5f)
                    lineTo(center.x - r * 0.866f, center.y + r * 0.5f)
                    close()
                }
                drawPath(upTri, color = WarmGold, style = Stroke(width = 1.4f, cap = StrokeCap.Round))

                // Downward Triangle (Sandalwood)
                val downTri = Path().apply {
                    val r = radius * 0.68f
                    moveTo(center.x, center.y + r)
                    lineTo(center.x + r * 0.866f, center.y - r * 0.5f)
                    lineTo(center.x - r * 0.866f, center.y - r * 0.5f)
                    close()
                }
                drawPath(downTri, color = SandalwoodContainer, style = Stroke(width = 1.4f, cap = StrokeCap.Round))
            }

            // Inner chakras
            drawCircle(
                color = WarmGold.copy(alpha = 0.75f),
                radius = radius * 0.38f,
                center = center,
                style = Stroke(width = 1.4f)
            )
            drawCircle(
                color = SandalwoodContainer,
                radius = radius * 0.22f,
                center = center,
                style = Stroke(width = 1.4f)
            )

            // Center Bindu
            drawCircle(
                color = SandalwoodContainer,
                radius = 4.dp.toPx(),
                center = center
            )

            // Cardinal dots
            val cardinalDist = radius * 0.86f
            val dotRadius = 2.5.dp.toPx()
            drawCircle(WarmGold, dotRadius, Offset(center.x, center.y - cardinalDist))
            drawCircle(WarmGold, dotRadius, Offset(center.x, center.y + cardinalDist))
            drawCircle(WarmGold, dotRadius, Offset(center.x - cardinalDist, center.y))
            drawCircle(WarmGold, dotRadius, Offset(center.x + cardinalDist, center.y))
        }
    }
}

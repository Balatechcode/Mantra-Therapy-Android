package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MantraEntity
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.theme.BotanicalSageTertiary
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
import com.example.ui.theme.OchreGoldFixed
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.PristineVellum
import com.example.ui.theme.SacredParchmentBg
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.WarmClaySubtle
import com.example.ui.theme.WarmGold

@Composable
fun PracticeModeScreen(
    mantra: MantraEntity,
    currentCount: Int,
    targetCount: Int,
    elapsedSeconds: Int,
    isPaused: Boolean,
    isCompletionDialogVisible: Boolean,
    onBeadTapped: () -> Unit,
    onTogglePause: () -> Unit,
    onResetPractice: () -> Unit,
    onDismissCompletion: () -> Unit,
    onClosePractice: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = (currentCount.toFloat() / targetCount.toFloat()).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 250),
        label = "japa_arc"
    )

    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60
    val timeFormatted = "%02d:%02d".format(minutes, seconds)

    // Calculated pace
    val pacePerMin = if (elapsedSeconds > 15) {
        ((currentCount.toFloat() / elapsedSeconds) * 60).toInt()
    } else {
        16
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("practice_mode_screen")
    ) {
        // Atmospheric mandala watermark
        SacredMandalaWatermark(
            modifier = Modifier.fillMaxSize(),
            color = SandalwoodPrimary,
            alpha = 0.06f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Exit Back & Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClosePractice,
                    modifier = Modifier.testTag("practice_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Exit Practice",
                        tint = SandalwoodPrimary
                    )
                }

                Text(
                    text = "Practice Mode",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    fontSize = 19.sp,
                    color = SandalwoodPrimary
                )

                IconButton(onClick = onTogglePause) {
                    Icon(
                        imageVector = if (isPaused) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                        contentDescription = "Pause",
                        tint = WarmClaySubtle
                    )
                }
            }

            // Mantra Title & Intention Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "DAILY RECITATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.5.sp,
                    color = OchreGoldSecondary
                )
                Text(
                    text = mantra.title,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = DeepUmberTitle
                )
                Text(
                    text = "$targetCount Japa Round • Focus on Breath",
                    fontSize = 13.sp,
                    color = WarmClaySubtle
                )
            }

            // Interactive Circular Rosary Bead Canvas (Large Tap Area)
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true, color = WarmGold),
                        onClick = onBeadTapped
                    )
                    .testTag("practice_rosary_tap_area"),
                contentAlignment = Alignment.Center
            ) {
                // Background Shadow Surface
                Surface(
                    modifier = Modifier.size(240.dp),
                    shape = CircleShape,
                    color = PristineVellum,
                    shadowElevation = 6.dp,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder)
                ) {}

                // Circular Arc Progress
                Canvas(modifier = Modifier.size(228.dp)) {
                    val strokeW = 9.dp.toPx()
                    val pad = strokeW / 2f
                    val arcSize = Size(size.width - strokeW, size.height - strokeW)

                    // Track Ring
                    drawArc(
                        color = SurfaceContainerHigh,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = Offset(pad, pad),
                        size = arcSize,
                        style = Stroke(width = strokeW, cap = StrokeCap.Round)
                    )

                    // Active Progress Ring
                    drawArc(
                        color = SandalwoodContainer,
                        startAngle = -90f,
                        sweepAngle = animatedProgress * 360f,
                        useCenter = false,
                        topLeft = Offset(pad, pad),
                        size = arcSize,
                        style = Stroke(width = strokeW, cap = StrokeCap.Round)
                    )

                    // Current Bead Tip Dot
                    if (animatedProgress > 0.01f) {
                        val angleRad = Math.toRadians((animatedProgress * 360.0 - 90.0)).toFloat()
                        val r = (size.width - strokeW) / 2f
                        val dotX = (size.width / 2f) + (r * kotlin.math.cos(angleRad))
                        val dotY = (size.height / 2f) + (r * kotlin.math.sin(angleRad))
                        drawCircle(
                            color = WarmGold,
                            radius = 6.dp.toPx(),
                            center = Offset(dotX, dotY)
                        )
                    }
                }

                // Inner Counter Display
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "$currentCount",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 52.sp,
                        lineHeight = 56.sp,
                        color = SandalwoodPrimary
                    )
                    Text(
                        text = "OF $targetCount",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp,
                        color = SandalwoodContainer
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(OchreGoldFixed.copy(alpha = 0.5f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.TouchApp,
                                contentDescription = null,
                                tint = OchreGoldSecondary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "TAP TO CHANT",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = OchreGoldSecondary
                            )
                        }
                    }
                }
            }

            // Sadhana Metrics (3 columns)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "TIME ELAPSED",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = timeFormatted,
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DeepUmberTitle
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(28.dp)
                            .background(SubtleSandstoneBorder)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "PACE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "~$pacePerMin / min",
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DeepUmberTitle
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(28.dp)
                            .background(SubtleSandstoneBorder)
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "ROUND",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "1 of 1",
                            fontFamily = FontFamily.Serif,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DeepUmberTitle
                        )
                    }
                }
            }

            // Sacred Cue Card (Devanagari snippet & mindful breathing reminder)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerHigh.copy(alpha = 0.5f),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, SubtleSandstoneBorder)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = mantra.devanagariShort,
                        fontFamily = FontFamily.Serif,
                        fontSize = 14.sp,
                        color = SandalwoodPrimary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Inhale peace • Exhale distraction",
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = WarmClaySubtle
                    )
                }
            }

            // Bottom Actions: Pause / Resume and Reset
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onTogglePause,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("practice_pause_resume_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SandalwoodContainer,
                        contentColor = PristineVellum
                    )
                ) {
                    Icon(
                        imageVector = if (isPaused) Icons.Filled.PlayArrow else Icons.Filled.Pause,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isPaused) "Resume Practice" else "Pause Practice",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                OutlinedButton(
                    onClick = onResetPractice,
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("practice_reset_button"),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = WarmClaySubtle
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Reset",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Completion Dialog
        if (isCompletionDialogVisible) {
            AlertDialog(
                onDismissRequest = onDismissCompletion,
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = BotanicalSageTertiary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Japa Complete",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = DeepUmberTitle
                        )
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "You completed $targetCount repetitions of ${mantra.title}.",
                            fontSize = 14.sp,
                            color = DeepUmberText
                        )
                        Text(
                            text = "Time elapsed: $timeFormatted. May this sacred sadhana bring clarity, peace, and auspicious fruit.",
                            fontSize = 13.sp,
                            color = WarmClaySubtle
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = onDismissCompletion,
                        colors = ButtonDefaults.buttonColors(containerColor = SandalwoodContainer),
                        modifier = Modifier.testTag("practice_complete_confirm_button")
                    ) {
                        Text("Save & Return", color = PristineVellum)
                    }
                },
                containerColor = PristineVellum,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

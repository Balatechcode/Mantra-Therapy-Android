package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DeepUmberTitle
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.PristineVellum
import com.example.ui.theme.SacredParchmentBg
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.WarmClaySubtle
import com.example.ui.theme.WarmGold

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentStep by remember { mutableIntStateOf(0) }

    val stepsContent = listOf(
        Triple(
            "SACRED INTENTION",
            "Discover Sacred Mantras",
            "Explore mantras organized around different purposes, practices and intentions—rooted in ancient wisdom for modern life."
        ),
        Triple(
            "PURPOSEFUL INQUIRY",
            "Search With Purpose",
            "Target mental clarity, material success, inner peace, and resolution power with sacred verses tailored to your path."
        ),
        Triple(
            "RITUAL ARCHITECTURE",
            "Practice Mindfully (108 Japa)",
            "Track rosary recitations with mindful tactile feedback, sound chimes, and persistent Sadhana progress logging."
        )
    )

    val currentInfo = stepsContent[currentStep]

    val infiniteTransition = rememberInfiniteTransition(label = "onboarding_mandala")
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring_spin"
    )

    Surface(
        modifier = modifier
            .fillMaxSize()
            .testTag("onboarding_screen"),
        color = SacredParchmentBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: PRARAMBHA • 01 & Skip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(SandalwoodContainer)
                    )
                    Text(
                        text = "PRARAMBHA • 0${currentStep + 1}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.5.sp,
                        color = WarmClaySubtle
                    )
                }

                TextButton(
                    onClick = onComplete,
                    modifier = Modifier.testTag("onboarding_skip_button")
                ) {
                    Text(
                        text = "Skip",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmClaySubtle
                    )
                }
            }

            // Visual Mandala Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Concentric Geometry Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .rotate(ringRotation)
                    ) {
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val r = size.minDimension / 2f

                        drawCircle(
                            color = WarmGold.copy(alpha = 0.25f),
                            radius = r * 0.96f,
                            center = center,
                            style = Stroke(width = 1.2f)
                        )
                        drawCircle(
                            color = SandalwoodContainer.copy(alpha = 0.3f),
                            radius = r * 0.82f,
                            center = center,
                            style = Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 6f), 0f))
                        )
                        drawCircle(
                            color = WarmGold.copy(alpha = 0.3f),
                            radius = r * 0.68f,
                            center = center,
                            style = Stroke(width = 1.2f)
                        )
                    }

                    // Central Shrine Emblem
                    Surface(
                        modifier = Modifier.size(92.dp),
                        shape = CircleShape,
                        color = PristineVellum,
                        shadowElevation = 4.dp,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "ॐ",
                                fontFamily = FontFamily.Serif,
                                fontSize = 28.sp,
                                color = SandalwoodContainer,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = "NAMAH",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                color = WarmGold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 3-step carousel indicator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0..2) {
                        if (i == currentStep) {
                            Box(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SandalwoodContainer)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(OutlineVariant.copy(alpha = 0.6f))
                                    .clickable { currentStep = i }
                            )
                        }
                    }
                }
            }

            // Editorial Typography Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = currentInfo.first,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp,
                    color = WarmGold
                )
                Text(
                    text = currentInfo.second,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    lineHeight = 30.sp,
                    color = DeepUmberTitle,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = currentInfo.third,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    color = WarmClaySubtle,
                    textAlign = TextAlign.Center
                )
            }

            // Path of Practice Preview Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PATH OF PRACTICE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            color = SandalwoodPrimary
                        )
                        Text(
                            text = "Stage ${currentStep + 1} / 3",
                            fontSize = 11.sp,
                            color = WarmClaySubtle
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(SubtleSandstoneBorder.copy(alpha = 0.5f))
                    )

                    // Step 1
                    MilestoneRow(
                        step = "1",
                        title = "Discover Sacred Mantras",
                        isActive = currentStep >= 0,
                        isCurrent = currentStep == 0
                    )
                    // Step 2
                    MilestoneRow(
                        step = "2",
                        title = "Search With Purpose",
                        isActive = currentStep >= 1,
                        isCurrent = currentStep == 1
                    )
                    // Step 3
                    MilestoneRow(
                        step = "3",
                        title = "Practice Mindfully (108 Japa Count)",
                        isActive = currentStep >= 2,
                        isCurrent = currentStep == 2
                    )
                }
            }

            // Bottom Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = {
                        if (currentStep < 2) {
                            currentStep++
                        } else {
                            onComplete()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("onboarding_get_started_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SandalwoodContainer,
                        contentColor = PristineVellum
                    )
                ) {
                    Text(
                        text = if (currentStep < 2) "Next" else "Get Started",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }

                TextButton(
                    onClick = onComplete,
                    modifier = Modifier.testTag("onboarding_already_practice_button")
                ) {
                    Text(
                        text = "I already have a practice",
                        fontSize = 13.sp,
                        color = WarmClaySubtle
                    )
                }
            }
        }
    }
}

@Composable
private fun MilestoneRow(
    step: String,
    title: String,
    isActive: Boolean,
    isCurrent: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isCurrent) SurfaceContainerHigh.copy(alpha = 0.7f) else Color.Transparent
            )
            .border(
                width = if (isCurrent) 1.dp else 0.dp,
                color = if (isCurrent) SandalwoodContainer.copy(alpha = 0.3f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(if (isActive) SandalwoodContainer else SurfaceContainerHighest),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = step,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) PristineVellum else WarmClaySubtle
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (isCurrent) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isActive) DeepUmberTitle else WarmClaySubtle,
            modifier = Modifier.weight(1f)
        )
        if (isActive) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = SandalwoodContainer,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

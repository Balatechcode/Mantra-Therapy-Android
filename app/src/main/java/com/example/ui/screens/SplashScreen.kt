package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnimatedSacredYantraEmblem
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.OutlineVariant
import com.example.ui.theme.SacredParchmentBg
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.WarmClaySubtle
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onEnterApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val breatheScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathe"
    )

    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    // Optional auto-advance after 3.5 seconds if untouched
    LaunchedEffect(Unit) {
        delay(3500L)
        onEnterApp()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("splash_screen")
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onEnterApp
            )
    ) {
        // Atmospheric concentric mandala watermark in backdrop
        SacredMandalaWatermark(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.4f),
            color = SandalwoodPrimary,
            alpha = 0.08f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: PRANAVA VANI
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "PRANAVA VANI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                    color = WarmClaySubtle
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(OchreGoldSecondary.copy(alpha = 0.8f))
                    )
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(OutlineVariant)
                    )
                }
            }

            // Center Identity & Sacred Emblem
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.scale(breatheScale)
            ) {
                // Sacred Geometry Yantra Emblem
                Box(contentAlignment = Alignment.Center) {
                    AnimatedSacredYantraEmblem(sizeDp = 144.dp)
                    Text(
                        text = "✦",
                        fontSize = 14.sp,
                        color = OchreGoldSecondary,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Title Typography
                Text(
                    text = "MANTRA",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Light,
                    fontSize = 38.sp,
                    lineHeight = 44.sp,
                    letterSpacing = 8.sp,
                    color = SandalwoodPrimary
                )
                Text(
                    text = "THERAPY",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    lineHeight = 30.sp,
                    letterSpacing = 9.sp,
                    color = OchreGoldSecondary
                )

                // Hairline Decorative Separator with small diamond
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.width(140.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color.Transparent, OutlineVariant)
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .size(6.dp)
                            .rotate(45f)
                            .background(SacredParchmentBg)
                            .clip(RoundedCornerShape(1.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(OchreGoldSecondary)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(OutlineVariant, Color.Transparent)
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ANCIENT WISDOM • MODERN PRACTICE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp,
                    color = WarmClaySubtle
                )
            }

            // Bottom Footer
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                // Shimmer loading bar
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(2.5.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(SubtleSandstoneBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(2.5.dp)
                            .align(Alignment.CenterStart)
                            .scale(scaleX = 1f, scaleY = 1f)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color.Transparent,
                                        SandalwoodContainer,
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Reverent Lineage
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(OchreGoldSecondary)
                    )
                    Text(
                        text = "ACHARYA MAHAPRAGYA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 2.5.sp,
                        color = WarmClaySubtle
                    )
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(OchreGoldSecondary)
                    )
                }

                Text(
                    text = "Preksha Meditation & Sound Healing",
                    fontSize = 11.sp,
                    color = WarmClaySubtle.copy(alpha = 0.75f),
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "ॐ अर्हम् नमः",
                    fontFamily = FontFamily.Serif,
                    fontSize = 15.sp,
                    color = SandalwoodContainer.copy(alpha = 0.6f),
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

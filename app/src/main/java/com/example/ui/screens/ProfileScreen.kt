package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.theme.BotanicalSageTertiary
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
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
fun ProfileScreen(
    totalRepetitions: Int,
    totalSessions: Int,
    vibrationEnabled: Boolean,
    onToggleVibration: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("profile_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header
            Box(modifier = Modifier.fillMaxWidth()) {
                SacredMandalaWatermark(
                    modifier = Modifier
                        .size(110.dp)
                        .align(Alignment.TopEnd),
                    color = SandalwoodPrimary,
                    alpha = 0.08f
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .border(2.dp, WarmGold.copy(alpha = 0.8f), CircleShape)
                            .background(PristineVellum)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(SurfaceContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ॐ",
                                fontFamily = FontFamily.Serif,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = SandalwoodContainer
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Sadhaka Practitioner",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = DeepUmberTitle
                        )
                        Text(
                            text = "Preksha Meditation & Japa Path",
                            fontSize = 12.sp,
                            color = WarmClaySubtle
                        )
                        Text(
                            text = "Brahma Muhurta Practice",
                            fontSize = 11.sp,
                            color = OchreGoldSecondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Stats Card
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
                        .padding(vertical = 14.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "REPETITIONS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "%,d".format(totalRepetitions),
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodPrimary
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
                            text = "SESSIONS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "$totalSessions",
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodPrimary
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
                            text = "STREAK",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp,
                            color = WarmClaySubtle
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "7 Days",
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = OchreGoldSecondary
                        )
                    }
                }
            }

            // Preferences
            Text(
                text = "PRACTICE PREFERENCES",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = SandalwoodPrimary
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Vibration setting
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Vibration,
                                contentDescription = null,
                                tint = SandalwoodContainer,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Bead Haptic Feedback",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DeepUmberTitle
                                )
                                Text(
                                    text = "Gentle tactile click on each Japa tap",
                                    fontSize = 11.sp,
                                    color = WarmClaySubtle
                                )
                            }
                        }

                        Switch(
                            checked = vibrationEnabled,
                            onCheckedChange = { onToggleVibration() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PristineVellum,
                                checkedTrackColor = SandalwoodContainer
                            ),
                            modifier = Modifier.testTag("vibration_switch")
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                            .height(1.dp)
                            .background(SubtleSandstoneBorder.copy(alpha = 0.6f))
                    )

                    // Daily Reminder
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = null,
                                tint = OchreGoldSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Daily Sadhana Reminder",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DeepUmberTitle
                                )
                                Text(
                                    text = "Brahma Muhurta • 06:00 AM",
                                    fontSize = 11.sp,
                                    color = WarmClaySubtle
                                )
                            }
                        }

                        Text(
                            text = "Active",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BotanicalSageTertiary
                        )
                    }
                }
            }

            // Sacred Lineage & Authorship Card
            Text(
                text = "SACRED LINEAGE & SOURCE",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = SandalwoodPrimary
            )

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = null,
                            tint = SandalwoodContainer,
                            modifier = Modifier.size(22.dp)
                        )
                        Column {
                            Text(
                                text = "Mantra Therapy & Preksha Dhyana",
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = DeepUmberTitle
                            )
                            Text(
                                text = "Authored by Acharya Mahapragya",
                                fontSize = 12.sp,
                                color = OchreGoldSecondary
                            )
                        }
                    }

                    Text(
                        text = "This application presents authentic sacred chants, procedures, and meanings curated from Acharya Mahapragya's seminal literature on sound vibration, contemplation, and inner transformation.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = DeepUmberText
                    )
                }
            }

            // About App
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = SurfaceContainerHigh.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = WarmClaySubtle,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Mantra Therapy v1.0 • Offline Digital Sadhana Sanctuary",
                        fontSize = 11.sp,
                        color = WarmClaySubtle
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

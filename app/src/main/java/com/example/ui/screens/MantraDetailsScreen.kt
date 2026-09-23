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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MantraEntity
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.components.SacredTopAppBar
import com.example.ui.theme.BotanicalSageTertiary
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
import com.example.ui.theme.MantraItalicStyle
import com.example.ui.theme.MantraScriptStyle
import com.example.ui.theme.OchreGoldFixed
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.PristineVellum
import com.example.ui.theme.SacredParchmentBg
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.WarmClaySubtle

@Composable
fun MantraDetailsScreen(
    mantra: MantraEntity,
    isPlayingAudio: Boolean,
    onToggleAudio: () -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    onStartPractice: (MantraEntity) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("mantra_details_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Sacred Top App Bar with back action & active favorite state
            SacredTopAppBar(
                title = "Mantra Therapy",
                showBackButton = true,
                isFavoriteActive = mantra.isFavorite,
                onBackClick = onBackClick,
                onFavoriteClick = { onToggleFavorite(mantra.id, mantra.isFavorite) }
            )

            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Details Card with Yantra watermark
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = PristineVellum,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    shadowElevation = 2.dp
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        SacredMandalaWatermark(
                            modifier = Modifier
                                .size(140.dp)
                                .align(Alignment.TopEnd),
                            color = SandalwoodPrimary,
                            alpha = 0.08f
                        )

                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Category Tag
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(OchreGoldFixed.copy(alpha = 0.5f))
                                    .padding(horizontal = 9.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "${mantra.number.uppercase()} • ${mantra.category.uppercase()}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp,
                                    color = OchreGoldSecondary
                                )
                            }

                            Text(
                                text = mantra.title,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = DeepUmberTitle
                            )

                            Text(
                                text = mantra.subtitle,
                                fontSize = 13.sp,
                                color = WarmClaySubtle
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(SubtleSandstoneBorder.copy(alpha = 0.6f))
                            )

                            // Action Row: Listen Audio & Repetition Frequency
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedButton(
                                    onClick = onToggleAudio,
                                    shape = RoundedCornerShape(20.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = if (isPlayingAudio) OchreGoldSecondary else SandalwoodContainer
                                    ),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isPlayingAudio) OchreGoldSecondary else SubtleSandstoneBorder
                                    ),
                                    modifier = Modifier
                                        .height(36.dp)
                                        .testTag("details_listen_audio_button")
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingAudio) Icons.Filled.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isPlayingAudio) "Playing Drone..." else "Listen Audio",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Repeat,
                                        contentDescription = null,
                                        tint = WarmClaySubtle,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Text(
                                        text = mantra.frequencyLabel,
                                        fontSize = 11.sp,
                                        color = WarmClaySubtle
                                    )
                                }
                            }
                        }
                    }
                }

                // Sanskrit / Devanagari Sacred Stanza Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = PristineVellum,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "DEVANAGARI SCRIPT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.5.sp,
                            color = OchreGoldSecondary
                        )

                        Text(
                            text = mantra.devanagariFull,
                            fontFamily = FontFamily.Serif,
                            fontSize = 20.sp,
                            lineHeight = 32.sp,
                            color = SandalwoodPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }

                // Transliteration Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = PristineVellum,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "TRANSLITERATION",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            color = WarmClaySubtle
                        )
                        Text(
                            text = mantra.transliteration,
                            style = MantraItalicStyle,
                            color = DeepUmberText,
                            lineHeight = 23.sp
                        )
                    }
                }

                // Meaning & Spiritual Significance Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = PristineVellum,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "SPIRITUAL SIGNIFICANCE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            color = WarmClaySubtle
                        )
                        Text(
                            text = mantra.meaning,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = DeepUmberText
                        )
                    }
                }

                // Procedure & Method Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = PristineVellum,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                    shadowElevation = 2.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Spa,
                                contentDescription = null,
                                tint = BotanicalSageTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "PROCEDURE & METHOD",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = SandalwoodPrimary
                            )
                        }
                        Text(
                            text = mantra.procedure,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = DeepUmberText
                        )
                    }
                }

                // Duration & Source Archive
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
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Duration & Commitment",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = DeepUmberTitle
                            )
                            Text(
                                text = mantra.durationDays,
                                fontSize = 12.sp,
                                color = OchreGoldSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(SubtleSandstoneBorder.copy(alpha = 0.5f))
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = WarmClaySubtle,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(
                                text = mantra.sourceArchive,
                                fontSize = 11.sp,
                                color = WarmClaySubtle
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            // Sticky Bottom Action Container for "Start Practice (108 Japa)"
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 6.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Button(
                        onClick = { onStartPractice(mantra) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("details_start_practice_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SandalwoodContainer,
                            contentColor = PristineVellum
                        )
                    ) {
                        Text(
                            text = "Start Practice (${mantra.targetRepetitions} Japa)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

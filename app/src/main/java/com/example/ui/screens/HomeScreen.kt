package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MantraEntity
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.theme.BotanicalSageTertiary
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
import com.example.ui.theme.MantraItalicStyle
import com.example.ui.theme.OchreGoldFixed
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.PristineVellum
import com.example.ui.theme.SacredParchmentBg
import com.example.ui.theme.SageFixed
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.WarmClaySubtle
import com.example.ui.theme.WarmGold

@Composable
fun HomeScreen(
    allMantras: List<MantraEntity>,
    selectedPurposeChip: String,
    onSelectPurposeChip: (String) -> Unit,
    onSearchClick: () -> Unit,
    onViewAllCategories: () -> Unit,
    onSelectMantra: (MantraEntity) -> Unit,
    onContinuePractice: (MantraEntity) -> Unit,
    onOpenProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Purpose chips matching design
    val purposeChips = listOf(
        "All", "Success", "Concentration", "Wealth", "Good Fortune", "Fame", "Resolution Power"
    )

    // Mantra of the day: Navkar / Auspicious Beginning
    val mantraOfDay = allMantras.firstOrNull { it.id == "auspicious_beginning" }
        ?: allMantras.firstOrNull()

    // Active practice: Attaining Success
    val activePracticeMantra = allMantras.firstOrNull { it.id == "attaining_success" }
        ?: allMantras.firstOrNull()

    // Recently viewed items
    val resolutionPowerMantra = allMantras.firstOrNull { it.id == "resolution_power" }
    val materialisticProsperityMantra = allMantras.firstOrNull { it.id == "materialistic_prosperity" }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("home_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Greeting & Practitioner Profile Avatar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Good Morning",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                        color = WarmClaySubtle
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Discover the right mantra for your purpose",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp,
                        lineHeight = 29.sp,
                        color = DeepUmberTitle
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Avatar with ॐ and online status
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, WarmGold.copy(alpha = 0.6f), CircleShape)
                        .background(PristineVellum)
                        .clickable { onOpenProfile() }
                        .padding(3.dp),
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
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SandalwoodContainer
                        )
                    }

                    // Online Mindful Green dot
                    Box(
                        modifier = Modifier
                            .size(11.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(BotanicalSageTertiary)
                            .border(2.dp, SacredParchmentBg, CircleShape)
                    )
                }
            }

            // Search Bar Input Container
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_search_bar")
                    .clickable { onSearchClick() },
                shape = RoundedCornerShape(16.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "What are you looking for?",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = WarmClaySubtle,
                        letterSpacing = 0.4.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = WarmClaySubtle,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Search mantra, purpose or problem...",
                            fontSize = 14.sp,
                            color = WarmClaySubtle.copy(alpha = 0.7f),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { onSearchClick() },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Voice Search",
                                tint = SandalwoodContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // Explore by Purpose (Horizontal Chips)
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Explore by Purpose",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DeepUmberTitle,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "View all",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = SandalwoodPrimary,
                        modifier = Modifier
                            .clickable { onViewAllCategories() }
                            .testTag("home_view_all_categories")
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    purposeChips.forEach { chip ->
                        val isSelected = chip == selectedPurposeChip
                        Box(
                            modifier = Modifier
                                .testTag("purpose_chip_$chip")
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    if (isSelected) SandalwoodContainer else PristineVellum
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) SandalwoodContainer else SubtleSandstoneBorder,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { onSelectPurposeChip(chip) }
                                .padding(horizontal = 16.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = chip,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) PristineVellum else WarmClaySubtle
                            )
                        }
                    }
                }
            }

            // Mantra of the Day Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mantra_of_the_day_card"),
                shape = RoundedCornerShape(18.dp),
                color = PristineVellum,
                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                shadowElevation = 3.dp
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Subtle Vector Mandala Watermark in upper-right
                    SacredMandalaWatermark(
                        modifier = Modifier
                            .size(160.dp)
                            .align(Alignment.TopEnd),
                        color = SandalwoodPrimary,
                        alpha = 0.08f
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Auspicious Tag
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(OchreGoldFixed.copy(alpha = 0.6f))
                                .border(1.dp, OchreGoldSecondary.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "MANTRA OF THE DAY • AUSPICIOUS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = OchreGoldSecondary
                            )
                        }

                        // Sacred Script
                        Text(
                            text = "नमो अरिहंताणं",
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            color = SandalwoodContainer,
                            letterSpacing = 0.5.sp
                        )

                        // Transliteration
                        Text(
                            text = "Namo arahantaanam",
                            style = MantraItalicStyle,
                            color = WarmClaySubtle
                        )

                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(1.dp)
                                .background(SubtleSandstoneBorder)
                        )

                        // Meaning
                        Text(
                            text = "“I bow to the enlightened souls who have conquered all inner enemies.”",
                            fontSize = 13.sp,
                            color = WarmClaySubtle,
                            lineHeight = 19.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Button(
                            onClick = { mantraOfDay?.let { onSelectMantra(it) } },
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(42.dp)
                                .testTag("view_mantra_of_the_day_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SandalwoodContainer,
                                contentColor = PristineVellum
                            )
                        ) {
                            Text(
                                text = "View Mantra",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }

            // Continue Practice Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "CONTINUE PRACTICE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    color = DeepUmberTitle
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
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = activePracticeMantra?.title ?: "Attaining Success (Bhaktamara)",
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = DeepUmberTitle
                                )
                                Text(
                                    text = "${activePracticeMantra?.currentPracticeCount ?: 47} / ${activePracticeMantra?.targetRepetitions ?: 108} repetitions",
                                    fontSize = 12.sp,
                                    color = WarmClaySubtle
                                )
                            }

                            Button(
                                onClick = { activePracticeMantra?.let { onContinuePractice(it) } },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SurfaceContainer,
                                    contentColor = SandalwoodContainer
                                ),
                                border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                                modifier = Modifier
                                    .height(34.dp)
                                    .testTag("continue_practice_button")
                            ) {
                                Text(
                                    text = "Continue",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Progress Bar (44%)
                        val progressRatio = (activePracticeMantra?.currentPracticeCount?.toFloat() ?: 47f) /
                                (activePracticeMantra?.targetRepetitions?.toFloat() ?: 108f)
                        val progressPercent = (progressRatio * 100).toInt().coerceIn(0, 100)

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SurfaceContainerHigh)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(progressRatio.coerceIn(0.05f, 1f))
                                        .height(7.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SandalwoodContainer)
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Ritual cycle",
                                    fontSize = 10.sp,
                                    color = WarmClaySubtle
                                )
                                Text(
                                    text = "$progressPercent% completed",
                                    fontSize = 10.sp,
                                    color = WarmClaySubtle
                                )
                            }
                        }
                    }
                }
            }

            // Recently Viewed Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "RECENTLY VIEWED",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = DeepUmberTitle
                    )
                    Text(
                        text = "Clear history",
                        fontSize = 11.sp,
                        color = WarmClaySubtle
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Card 1: Resolution Power
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { resolutionPowerMantra?.let { onSelectMantra(it) } }
                            .testTag("recent_card_resolution_power"),
                        shape = RoundedCornerShape(16.dp),
                        color = PristineVellum,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(OchreGoldFixed.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Spa,
                                        contentDescription = null,
                                        tint = OchreGoldSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = WarmClaySubtle,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "Resolution Power",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DeepUmberTitle
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "ॐ ह्रीं नमो सिद्धाणं",
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 12.sp,
                                    color = SandalwoodPrimary
                                )
                            }

                            Text(
                                text = "Chanted 2d ago",
                                fontSize = 11.sp,
                                color = WarmClaySubtle
                            )
                        }
                    }

                    // Card 2: Materialistic Prosperity
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { materialisticProsperityMantra?.let { onSelectMantra(it) } }
                            .testTag("recent_card_materialistic_prosperity"),
                        shape = RoundedCornerShape(16.dp),
                        color = PristineVellum,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                        shadowElevation = 2.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(SageFixed.copy(alpha = 0.6f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AutoAwesome,
                                        contentDescription = null,
                                        tint = BotanicalSageTertiary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = WarmClaySubtle,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "Materialistic Prosperity",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DeepUmberTitle
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "ॐ श्रीं महालक्ष्म्यै नमः",
                                    fontFamily = FontFamily.Serif,
                                    fontSize = 12.sp,
                                    color = SandalwoodPrimary
                                )
                            }

                            Text(
                                text = "Chanted yesterday",
                                fontSize = 11.sp,
                                color = WarmClaySubtle
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

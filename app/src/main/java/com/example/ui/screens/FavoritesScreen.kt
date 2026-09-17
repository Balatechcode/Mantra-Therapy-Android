package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import com.example.data.MantraEntity
import com.example.ui.components.SacredMandalaWatermark
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
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
fun FavoritesScreen(
    favoriteMantras: List<MantraEntity>,
    onSelectMantra: (MantraEntity) -> Unit,
    onStartPractice: (MantraEntity) -> Unit,
    onToggleFavorite: (String, Boolean) -> Unit,
    onExploreMantras: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("favorites_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                SacredMandalaWatermark(
                    modifier = Modifier
                        .size(110.dp)
                        .align(Alignment.TopEnd),
                    color = SandalwoodPrimary,
                    alpha = 0.08f
                )

                Column {
                    Text(
                        text = "Sacred Favorites",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = DeepUmberTitle
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Your bookmarked mantras & daily sadhanas",
                        fontSize = 13.sp,
                        color = WarmClaySubtle
                    )
                }
            }

            if (favoriteMantras.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            tint = WarmClaySubtle,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "No saved mantras yet",
                            fontFamily = FontFamily.Serif,
                            fontSize = 18.sp,
                            color = DeepUmberTitle
                        )
                        Text(
                            text = "Tap the heart icon on any mantra to keep it in your personal sanctuary.",
                            fontSize = 13.sp,
                            color = WarmClaySubtle,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = onExploreMantras,
                            colors = ButtonDefaults.buttonColors(containerColor = SandalwoodContainer),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("favorites_explore_button")
                        ) {
                            Text("Explore Mantras", color = PristineVellum)
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${favoriteMantras.size} SAVED PRACTICES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(favoriteMantras) { mantra ->
                        FavoriteMantraCard(
                            mantra = mantra,
                            onCardClick = { onSelectMantra(mantra) },
                            onPracticeClick = { onStartPractice(mantra) },
                            onFavoriteClick = { onToggleFavorite(mantra.id, true) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteMantraCard(
    mantra: MantraEntity,
    onCardClick: () -> Unit,
    onPracticeClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("favorite_card_${mantra.id}"),
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
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = mantra.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        color = DeepUmberTitle
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = mantra.subtitle,
                        fontSize = 12.sp,
                        color = WarmClaySubtle
                    )
                }

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Remove Favorite",
                        tint = OchreGoldSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = SurfaceContainerHighest.copy(alpha = 0.5f)
            ) {
                Text(
                    text = mantra.devanagariShort,
                    fontFamily = FontFamily.Serif,
                    fontSize = 13.sp,
                    color = SandalwoodContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mantra.frequencyLabel,
                    fontSize = 11.sp,
                    color = WarmClaySubtle
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onPracticeClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SandalwoodContainer,
                            contentColor = PristineVellum
                        ),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Practice",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = onCardClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainer,
                            contentColor = DeepUmberTitle
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = "Details",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

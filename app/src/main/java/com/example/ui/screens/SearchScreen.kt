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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.example.ui.theme.DeepUmberText
import com.example.ui.theme.DeepUmberTitle
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
fun SearchScreen(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchFilterDimension: String,
    onSearchFilterDimensionChange: (String) -> Unit,
    recentSearches: List<String>,
    onSelectRecentSearch: (String) -> Unit,
    onClearHistory: () -> Unit,
    allMantras: List<MantraEntity>,
    onSelectMantra: (MantraEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = listOf("Purpose", "Problem", "Mantra", "Category")

    val filteredMantras = remember(searchQuery, searchFilterDimension, allMantras) {
        if (searchQuery.isBlank()) {
            allMantras.take(3)
        } else {
            val q = searchQuery.trim().lowercase()
            allMantras.filter { mantra ->
                mantra.title.lowercase().contains(q) ||
                        mantra.subtitle.lowercase().contains(q) ||
                        mantra.category.lowercase().contains(q) ||
                        mantra.purposeGroup.lowercase().contains(q) ||
                        mantra.tags.lowercase().contains(q) ||
                        mantra.devanagariShort.contains(q) ||
                        mantra.transliteration.lowercase().contains(q) ||
                        mantra.benefit.lowercase().contains(q)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("search_screen")
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Search Input Field
            item {
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
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = SandalwoodContainer,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = onSearchQueryChange,
                            placeholder = {
                                Text(
                                    text = "Search by purpose, problem...",
                                    fontSize = 14.sp,
                                    color = WarmClaySubtle.copy(alpha = 0.7f)
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = DeepUmberText,
                                unfocusedTextColor = DeepUmberText
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("search_text_input")
                        )
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchQueryChange("") },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear",
                                    tint = WarmClaySubtle,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Search Filter Dimensions Chips (Purpose, Problem, Mantra, Category)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    dimensions.forEach { dim ->
                        val isSelected = dim == searchFilterDimension
                        Box(
                            modifier = Modifier
                                .testTag("search_dimension_$dim")
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) SandalwoodContainer else PristineVellum)
                                .border(
                                    1.dp,
                                    if (isSelected) SandalwoodContainer else SubtleSandstoneBorder,
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { onSearchFilterDimensionChange(dim) }
                                .padding(horizontal = 16.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = dim,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isSelected) PristineVellum else WarmClaySubtle
                            )
                        }
                    }
                }
            }

            // Recent Searches (if active)
            if (recentSearches.isNotEmpty() && searchQuery.isEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "RECENT SEARCHES",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = WarmClaySubtle
                            )
                            Text(
                                text = "Clear history",
                                fontSize = 11.sp,
                                color = SandalwoodPrimary,
                                modifier = Modifier
                                    .clickable { onClearHistory() }
                                    .testTag("search_clear_history")
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            recentSearches.forEach { searchItem ->
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onSelectRecentSearch(searchItem) },
                                    shape = RoundedCornerShape(10.dp),
                                    color = PristineVellum,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AccessTime,
                                            contentDescription = null,
                                            tint = WarmClaySubtle,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = searchItem,
                                            fontSize = 13.sp,
                                            color = DeepUmberText
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Search Results Heading
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredMantras.size} PRACTICES FOUND",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = SandalwoodPrimary
                    )
                }
            }

            // Results List
            items(filteredMantras) { mantra ->
                MantraResultCard(
                    mantra = mantra,
                    onClick = { onSelectMantra(mantra) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun MantraResultCard(
    mantra: MantraEntity,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("mantra_card_${mantra.id}")
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = PristineVellum,
        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header row with Title and Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mantra.title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
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

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = mantra.number,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SandalwoodPrimary
                    )
                }
            }

            // Devanagari Script Highlight Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = SurfaceContainerHighest.copy(alpha = 0.5f),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, SubtleSandstoneBorder)
            ) {
                Text(
                    text = mantra.devanagariShort,
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    color = SandalwoodContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }

            // Tags & Frequency Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tags
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    mantra.tags.split(",").take(2).forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(OchreGoldFixed.copy(alpha = 0.4f))
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = tag.trim(),
                                fontSize = 10.sp,
                                color = OchreGoldSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Timing / Frequency
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val icon = when (mantra.timingIcon) {
                        "wb_sunny" -> Icons.Filled.WbSunny
                        "light_mode" -> Icons.Filled.LightMode
                        else -> Icons.Filled.Repeat
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = WarmClaySubtle,
                        modifier = Modifier.size(14.dp)
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

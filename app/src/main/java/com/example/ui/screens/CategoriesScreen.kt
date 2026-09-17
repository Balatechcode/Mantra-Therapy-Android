package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FilterDrama
import androidx.compose.material.icons.filled.Flare
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.WarmClaySubtle

data class CategoryItem(
    val title: String,
    val practiceCount: String,
    val subtitle: String,
    val icon: ImageVector,
    val isGold: Boolean = false
)

@Composable
fun CategoriesScreen(
    onSelectCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf(
        CategoryItem("Namaskar Mahamantra", "5 practices", "Foundation of Jain practice", Icons.Filled.SelfImprovement),
        CategoryItem("Auspicious Beginning", "8 practices", "Morning & commencement", Icons.Filled.Flare, isGold = true),
        CategoryItem("Rare Things", "4 practices", "Attaining difficult goals", Icons.Filled.Diamond),
        CategoryItem("Success", "12 practices", "Victory & fulfillment", Icons.Filled.EmojiEvents, isGold = true),
        CategoryItem("Resolution Power", "7 practices", "Willpower & determination", Icons.Filled.Psychology),
        CategoryItem("Concentration", "9 practices", "Focus & dharana", Icons.Filled.Spa, isGold = true),
        CategoryItem("Good Fortune", "6 practices", "Auspicious destiny", Icons.Filled.Stars, isGold = true),
        CategoryItem("Fame", "5 practices", "Honor & respect", Icons.Filled.Brightness5),
        CategoryItem("Vachan Siddhi", "4 practices", "Power of truth & speech", Icons.Filled.RecordVoiceOver),
        CategoryItem("Materialistic Prosperity", "11 practices", "Financial abundance", Icons.Filled.AutoAwesome, isGold = true),
        CategoryItem("Wealth & Welfare", "8 practices", "Total well-being", Icons.Filled.FilterDrama, isGold = true),
        CategoryItem("Spiritual Prosperity", "10 practices", "Inner liberation", Icons.Filled.ModeNight)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SacredParchmentBg)
            .testTag("categories_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            // Header with Yantra watermark in background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Subtle Sacred Watermark
                SacredMandalaWatermark(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.TopEnd),
                    color = SandalwoodPrimary,
                    alpha = 0.09f
                )

                Column {
                    Text(
                        text = "Explore Mantras",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = DeepUmberTitle,
                        letterSpacing = (-0.01).sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Browse practices by purpose.",
                        fontSize = 13.sp,
                        color = WarmClaySubtle
                    )
                }
            }

            // 2-Column Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(categories) { item ->
                    CategoryCard(
                        item = item,
                        onClick = { onSelectCategory(item.title) }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun CategoryCard(
    item: CategoryItem,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(138.dp)
            .testTag("category_card_${item.title}")
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = PristineVellum,
        border = androidx.compose.foundation.BorderStroke(1.dp, SubtleSandstoneBorder),
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (item.isGold) OchreGoldSecondary else SandalwoodContainer,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = item.practiceCount,
                    fontSize = 11.sp,
                    color = WarmClaySubtle
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = item.title,
                    fontFamily = FontFamily.Serif,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 19.sp,
                    color = DeepUmberText
                )
                Text(
                    text = item.subtitle,
                    fontSize = 11.sp,
                    color = WarmClaySubtle,
                    lineHeight = 14.sp,
                    maxLines = 2
                )
            }
        }
    }
}

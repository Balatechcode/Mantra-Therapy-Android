package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.OchreGoldFixed
import com.example.ui.theme.OchreGoldSecondary
import com.example.ui.theme.PristineVellum
import com.example.ui.theme.SandalwoodContainer
import com.example.ui.theme.SandalwoodPrimary
import com.example.ui.theme.SubtleSandstoneBorder
import com.example.ui.theme.WarmClaySubtle

enum class AppDestination {
    HOME,
    SEARCH,
    CATEGORIES,
    FAVORITES,
    PROFILE
}

@Composable
fun SacredTopAppBar(
    title: String = "Mantra Therapy",
    showBackButton: Boolean = true,
    showBindu: Boolean = true,
    isFavoriteActive: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    onFavoriteClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Leading Back Action
                if (showBackButton && onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("top_bar_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = SandalwoodPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }

                // Center Title with Sacred Bindu
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 21.sp,
                        color = SandalwoodPrimary,
                        letterSpacing = 0.5.sp
                    )
                    if (showBindu) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(OchreGoldSecondary)
                        )
                    }
                }

                // Trailing Favorite Action
                if (onFavoriteClick != null) {
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("top_bar_favorite_button")
                    ) {
                        Icon(
                            imageVector = if (isFavoriteActive) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorites",
                            tint = if (isFavoriteActive) OchreGoldSecondary else SandalwoodPrimary,
                            modifier = Modifier.size(23.dp)
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }

            // Hairline sandstone divider line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SubtleSandstoneBorder.copy(alpha = 0.6f))
            )
        }
    }
}

@Composable
fun SacredBottomNavBar(
    currentDestination: AppDestination,
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp),
        color = PristineVellum,
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SubtleSandstoneBorder.copy(alpha = 0.7f))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavItem(
                    label = "Home",
                    iconFilled = Icons.Filled.SelfImprovement,
                    iconOutlined = Icons.Outlined.SelfImprovement,
                    isSelected = currentDestination == AppDestination.HOME,
                    testTag = "nav_home",
                    onClick = { onNavigate(AppDestination.HOME) }
                )
                NavItem(
                    label = "Search",
                    iconFilled = Icons.Filled.Search,
                    iconOutlined = Icons.Outlined.Search,
                    isSelected = currentDestination == AppDestination.SEARCH,
                    testTag = "nav_search",
                    onClick = { onNavigate(AppDestination.SEARCH) }
                )
                NavItem(
                    label = "Categories",
                    iconFilled = Icons.Filled.GridView,
                    iconOutlined = Icons.Outlined.GridView,
                    isSelected = currentDestination == AppDestination.CATEGORIES,
                    testTag = "nav_categories",
                    onClick = { onNavigate(AppDestination.CATEGORIES) }
                )
                NavItem(
                    label = "Favorites",
                    iconFilled = Icons.Filled.Favorite,
                    iconOutlined = Icons.Outlined.FavoriteBorder,
                    isSelected = currentDestination == AppDestination.FAVORITES,
                    testTag = "nav_favorites",
                    onClick = { onNavigate(AppDestination.FAVORITES) }
                )
                NavItem(
                    label = "Profile",
                    iconFilled = Icons.Filled.Person,
                    iconOutlined = Icons.Outlined.Person,
                    isSelected = currentDestination == AppDestination.PROFILE,
                    testTag = "nav_profile",
                    onClick = { onNavigate(AppDestination.PROFILE) }
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    iconFilled: ImageVector,
    iconOutlined: ImageVector,
    isSelected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) OchreGoldFixed.copy(alpha = 0.45f) else Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true, radius = 28.dp),
                onClick = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isSelected) iconFilled else iconOutlined,
                contentDescription = label,
                tint = if (isSelected) OchreGoldSecondary else WarmClaySubtle,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) OchreGoldSecondary else WarmClaySubtle
            )
        }
    }
}

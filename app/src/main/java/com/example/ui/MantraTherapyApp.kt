package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppDestination
import com.example.ui.components.SacredBottomNavBar
import com.example.ui.components.SacredTopAppBar
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MantraDetailsScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.PracticeModeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MantraTherapyTheme

@Composable
fun MantraTherapyApp(
    viewModel: MantraViewModel = viewModel()
) {
    val activeOverlay by viewModel.activeOverlayScreen.collectAsState()
    val currentDestination by viewModel.currentDestination.collectAsState()
    val allMantras by viewModel.allMantras.collectAsState()
    val favoriteMantras by viewModel.favoriteMantras.collectAsState()
    val selectedMantra by viewModel.selectedMantra.collectAsState()
    val selectedPurposeChip by viewModel.selectedPurposeChip.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchDimension by viewModel.searchFilterDimension.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val japaCount by viewModel.japaCount.collectAsState()
    val japaTarget by viewModel.japaTarget.collectAsState()
    val elapsedSeconds by viewModel.elapsedSeconds.collectAsState()
    val isPaused by viewModel.isJapaPaused.collectAsState()
    val isCompletionDialogVisible by viewModel.isCompletionDialogVisible.collectAsState()
    val isPlayingAudio by viewModel.isPlayingAudio.collectAsState()
    val totalRepetitions by viewModel.totalRepetitions.collectAsState()
    val totalSessions by viewModel.totalSessions.collectAsState()
    val vibrationEnabled by viewModel.vibrationEnabled.collectAsState()
    val profile by viewModel.profile.collectAsState()

    MantraTherapyTheme {
        // System Back Handling
        BackHandler(enabled = activeOverlay != null || currentDestination != AppDestination.HOME) {
            when {
                activeOverlay == "practice" -> viewModel.closeOverlay()
                activeOverlay == "details" -> viewModel.closeOverlay()
                activeOverlay == "onboarding" -> viewModel.dismissOnboarding()
                activeOverlay == "splash" -> viewModel.dismissSplash()
                currentDestination != AppDestination.HOME -> viewModel.setDestination(AppDestination.HOME)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (activeOverlay) {
                "splash" -> {
                    SplashScreen(
                        onEnterApp = { viewModel.dismissSplash() }
                    )
                }
                "onboarding" -> {
                    OnboardingScreen(
                        onComplete = { viewModel.dismissOnboarding() }
                    )
                }
                "practice" -> {
                    selectedMantra?.let { mantra ->
                        PracticeModeScreen(
                            mantra = mantra,
                            currentCount = japaCount,
                            targetCount = japaTarget,
                            elapsedSeconds = elapsedSeconds,
                            isPaused = isPaused,
                            isCompletionDialogVisible = isCompletionDialogVisible,
                            onBeadTapped = { viewModel.onBeadTapped() },
                            onTogglePause = { viewModel.togglePauseJapa() },
                            onResetPractice = { viewModel.restartJapa() },
                            onDismissCompletion = { viewModel.dismissCompletionDialog() },
                            onClosePractice = { viewModel.closeOverlay() }
                        )
                    }
                }
                "details" -> {
                    selectedMantra?.let { mantra ->
                        MantraDetailsScreen(
                            mantra = mantra,
                            isPlayingAudio = isPlayingAudio,
                            onToggleAudio = { viewModel.toggleAudio() },
                            onToggleFavorite = { id, fav -> viewModel.toggleFavorite(id, fav) },
                            onStartPractice = { m -> viewModel.startPractice(m) },
                            onBackClick = { viewModel.closeOverlay() }
                        )
                    }
                }
                else -> {
                    // Main 5-tab application frame
                    Scaffold(
                        topBar = {
                            SacredTopAppBar(
                                title = "Mantra Therapy",
                                showBackButton = currentDestination != AppDestination.HOME,
                                showBindu = true,
                                isFavoriteActive = false,
                                onBackClick = if (currentDestination != AppDestination.HOME) {
                                    { viewModel.setDestination(AppDestination.HOME) }
                                } else null,
                                onFavoriteClick = { viewModel.setDestination(AppDestination.FAVORITES) }
                            )
                        },
                        bottomBar = {
                            SacredBottomNavBar(
                                currentDestination = currentDestination,
                                onNavigate = { dest -> viewModel.setDestination(dest) }
                            )
                        }
                    ) { innerPadding ->
                        AnimatedContent(
                            targetState = currentDestination,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            label = "tab_switch"
                        ) { destination ->
                            when (destination) {
                                AppDestination.HOME -> {
                                    HomeScreen(
                                        allMantras = allMantras,
                                        selectedPurposeChip = selectedPurposeChip,
                                        onSelectPurposeChip = { chip ->
                                            viewModel.selectPurposeChip(chip)
                                            if (chip != "All") {
                                                viewModel.setSearchQuery(chip)
                                                viewModel.setDestination(AppDestination.SEARCH)
                                            }
                                        },
                                        onSearchClick = { viewModel.setDestination(AppDestination.SEARCH) },
                                        onViewAllCategories = { viewModel.setDestination(AppDestination.CATEGORIES) },
                                        onSelectMantra = { mantra -> viewModel.openMantraDetails(mantra) },
                                        onContinuePractice = { mantra -> viewModel.startPractice(mantra) },
                                        onOpenProfile = { viewModel.setDestination(AppDestination.PROFILE) },
                                        practitionerAvatar = profile.avatarSymbol,
                                        practitionerName = profile.name
                                    )
                                }
                                AppDestination.SEARCH -> {
                                    SearchScreen(
                                        searchQuery = searchQuery,
                                        onSearchQueryChange = { q -> viewModel.setSearchQuery(q) },
                                        searchFilterDimension = searchDimension,
                                        onSearchFilterDimensionChange = { dim -> viewModel.setSearchFilterDimension(dim) },
                                        recentSearches = recentSearches,
                                        onSelectRecentSearch = { item -> viewModel.setSearchQuery(item) },
                                        onClearHistory = { viewModel.clearRecentSearches() },
                                        allMantras = allMantras,
                                        onSelectMantra = { mantra -> viewModel.openMantraDetails(mantra) }
                                    )
                                }
                                AppDestination.CATEGORIES -> {
                                    CategoriesScreen(
                                        onSelectCategory = { category -> viewModel.selectCategory(category) }
                                    )
                                }
                                AppDestination.FAVORITES -> {
                                    FavoritesScreen(
                                        favoriteMantras = favoriteMantras,
                                        onSelectMantra = { mantra -> viewModel.openMantraDetails(mantra) },
                                        onStartPractice = { mantra -> viewModel.startPractice(mantra) },
                                        onToggleFavorite = { id, fav -> viewModel.toggleFavorite(id, fav) },
                                        onExploreMantras = { viewModel.setDestination(AppDestination.CATEGORIES) }
                                    )
                                }
                                AppDestination.PROFILE -> {
                                    ProfileScreen(
                                        profile = profile,
                                        totalRepetitions = totalRepetitions,
                                        totalSessions = totalSessions,
                                        vibrationEnabled = vibrationEnabled,
                                        onToggleVibration = { viewModel.toggleVibration() },
                                        onSaveProfile = { name, path, time, avatar, goal, sankalpa ->
                                            viewModel.updateProfile(name, path, time, avatar, goal, sankalpa)
                                        },
                                        onResetProfile = {
                                            viewModel.resetProfileToDefaults()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

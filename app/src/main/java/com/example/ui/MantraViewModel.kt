package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SacredSoundPlayer
import com.example.data.AppDatabase
import com.example.data.MantraEntity
import com.example.data.MantraRepository
import com.example.ui.components.AppDestination
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MantraViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MantraRepository
    val soundPlayer: SacredSoundPlayer = SacredSoundPlayer(application)

    init {
        val db = AppDatabase.getDatabase(application)
        repository = MantraRepository(db.mantraDao())
        viewModelScope.launch {
            repository.initializeSeedDataIfNeeded()
        }
    }

    val allMantras: StateFlow<List<MantraEntity>> = repository.allMantras
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteMantras: StateFlow<List<MantraEntity>> = repository.favoriteMantras
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalRepetitions: StateFlow<Int> = repository.totalRepetitions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 2592)

    val totalSessions: StateFlow<Int> = repository.totalSessionCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 24)

    // Current navigation tab
    private val _currentDestination = MutableStateFlow(AppDestination.HOME)
    val currentDestination: StateFlow<AppDestination> = _currentDestination.asStateFlow()

    // Active Screen overlay (Splash, Onboarding, Details, Practice, null for tab screen)
    private val _activeOverlayScreen = MutableStateFlow<String?>("splash") // Starts with splash, can be dismissed
    val activeOverlayScreen: StateFlow<String?> = _activeOverlayScreen.asStateFlow()

    // Selected Mantra for Details / Practice
    private val _selectedMantra = MutableStateFlow<MantraEntity?>(null)
    val selectedMantra: StateFlow<MantraEntity?> = _selectedMantra.asStateFlow()

    // Selected Category for filtering
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    // Selected Purpose chip in Home Screen
    private val _selectedPurposeChip = MutableStateFlow("All")
    val selectedPurposeChip: StateFlow<String> = _selectedPurposeChip.asStateFlow()

    // Search state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchFilterDimension = MutableStateFlow("Purpose")
    val searchFilterDimension: StateFlow<String> = _searchFilterDimension.asStateFlow()

    private val _recentSearches = MutableStateFlow(
        listOf("Success in business", "Concentration for meditation", "Namaskar Mahamantra 108")
    )
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    // Japa Practice State
    private val _japaCount = MutableStateFlow(47)
    val japaCount: StateFlow<Int> = _japaCount.asStateFlow()

    private val _japaTarget = MutableStateFlow(108)
    val japaTarget: StateFlow<Int> = _japaTarget.asStateFlow()

    private val _elapsedSeconds = MutableStateFlow(763) // 12:43
    val elapsedSeconds: StateFlow<Int> = _elapsedSeconds.asStateFlow()

    private val _isJapaPaused = MutableStateFlow(false)
    val isJapaPaused: StateFlow<Boolean> = _isJapaPaused.asStateFlow()

    private val _isCompletionDialogVisible = MutableStateFlow(false)
    val isCompletionDialogVisible: StateFlow<Boolean> = _isCompletionDialogVisible.asStateFlow()

    // Audio chant drone state
    private val _isPlayingAudio = MutableStateFlow(false)
    val isPlayingAudio: StateFlow<Boolean> = _isPlayingAudio.asStateFlow()

    // Preferences
    private val _vibrationEnabled = MutableStateFlow(true)
    val vibrationEnabled: StateFlow<Boolean> = _vibrationEnabled.asStateFlow()

    private val _dailyReminderTime = MutableStateFlow("Brahma Muhurta • 06:00 AM")
    val dailyReminderTime: StateFlow<String> = _dailyReminderTime.asStateFlow()

    private var japaTimerJob: Job? = null

    init {
        // Find default initial mantra (Attaining Success)
        viewModelScope.launch {
            allMantras.collect { list ->
                if (_selectedMantra.value == null && list.isNotEmpty()) {
                    _selectedMantra.value = list.firstOrNull { it.id == "attaining_success" } ?: list.first()
                }
            }
        }
    }

    fun setDestination(dest: AppDestination) {
        _activeOverlayScreen.value = null
        _currentDestination.value = dest
    }

    fun openSplash() {
        _activeOverlayScreen.value = "splash"
    }

    fun dismissSplash() {
        _activeOverlayScreen.value = "onboarding"
    }

    fun openOnboarding() {
        _activeOverlayScreen.value = "onboarding"
    }

    fun dismissOnboarding() {
        _activeOverlayScreen.value = null
        _currentDestination.value = AppDestination.HOME
    }

    fun openMantraDetails(mantra: MantraEntity) {
        _selectedMantra.value = mantra
        _activeOverlayScreen.value = "details"
    }

    fun closeOverlay() {
        stopAudio()
        _activeOverlayScreen.value = null
    }

    fun startPractice(mantra: MantraEntity? = null) {
        val target = mantra ?: _selectedMantra.value ?: allMantras.value.firstOrNull()
        if (target != null) {
            _selectedMantra.value = target
            _japaTarget.value = target.targetRepetitions
            _japaCount.value = if (target.id == "attaining_success") 47 else 0
            _elapsedSeconds.value = if (target.id == "attaining_success") 763 else 0
            _isJapaPaused.value = false
            _isCompletionDialogVisible.value = false
            startTimer()
            _activeOverlayScreen.value = "practice"
        }
    }

    fun onBeadTapped() {
        if (_isJapaPaused.value) return
        val current = _japaCount.value
        val target = _japaTarget.value

        if (current < target) {
            val newCount = current + 1
            _japaCount.value = newCount

            if (_vibrationEnabled.value) {
                soundPlayer.triggerBeadHaptic()
            }
            soundPlayer.playBeadClick()

            _selectedMantra.value?.let { mantra ->
                viewModelScope.launch {
                    repository.updatePracticeProgress(mantra.id, newCount)
                }
            }

            if (newCount >= target) {
                // Completed round!
                soundPlayer.triggerCompletionHaptic()
                soundPlayer.playSacredChime()
                _isCompletionDialogVisible.value = true
                stopTimer()

                _selectedMantra.value?.let { mantra ->
                    viewModelScope.launch {
                        repository.recordPracticeSession(
                            mantraId = mantra.id,
                            mantraTitle = mantra.title,
                            repetitions = target,
                            durationSeconds = _elapsedSeconds.value
                        )
                    }
                }
            }
        }
    }

    fun togglePauseJapa() {
        _isJapaPaused.value = !_isJapaPaused.value
        if (_isJapaPaused.value) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    fun restartJapa() {
        _japaCount.value = 0
        _elapsedSeconds.value = 0
        _isJapaPaused.value = false
        _isCompletionDialogVisible.value = false
        startTimer()
    }

    fun dismissCompletionDialog() {
        _isCompletionDialogVisible.value = false
        _activeOverlayScreen.value = "details"
    }

    private fun startTimer() {
        japaTimerJob?.cancel()
        japaTimerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000L)
                if (!_isJapaPaused.value && !_isCompletionDialogVisible.value) {
                    _elapsedSeconds.value += 1
                }
            }
        }
    }

    private fun stopTimer() {
        japaTimerJob?.cancel()
        japaTimerJob = null
    }

    fun toggleFavorite(id: String, currentVal: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(id, !currentVal)
        }
    }

    fun toggleAudio() {
        soundPlayer.toggleChantDrone { playing ->
            _isPlayingAudio.value = playing
        }
    }

    fun stopAudio() {
        soundPlayer.stopChantDrone()
        _isPlayingAudio.value = false
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank() && !_recentSearches.value.contains(query)) {
            _recentSearches.value = listOf(query) + _recentSearches.value.take(4)
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun setSearchFilterDimension(dimension: String) {
        _searchFilterDimension.value = dimension
    }

    fun clearRecentSearches() {
        _recentSearches.value = emptyList()
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _searchQuery.value = category
        _currentDestination.value = AppDestination.SEARCH
        _activeOverlayScreen.value = null
    }

    fun selectPurposeChip(chip: String) {
        _selectedPurposeChip.value = chip
    }

    fun toggleVibration() {
        _vibrationEnabled.value = !_vibrationEnabled.value
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        soundPlayer.stopChantDrone()
    }
}

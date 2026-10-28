package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.MantraRepository
import com.example.ui.MantraViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun testAppName() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Mantra Therapy", appName)
    }

    @Test
    fun testSeedMantrasPopulated() = runBlocking {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val db = AppDatabase.getDatabase(app)
        val repo = MantraRepository(db.mantraDao())
        repo.initializeSeedDataIfNeeded()

        val list = repo.allMantras.first()
        assertEquals(19, list.size)

        val namaskar = list.find { it.id == "mantra_01_namaskar" }
        assertNotNull(namaskar)
        assertEquals("Mantra 01", namaskar?.number)
        assertEquals("Namaskar Mahamantra", namaskar?.title)
        assertEquals("Auspiciousness", namaskar?.purposeGroup)

        val attainingSuccess = list.find { it.id == "mantra_04_attaining_success" }
        assertNotNull(attainingSuccess)
        assertEquals("Mantra 04", attainingSuccess?.number)
        assertEquals(108, attainingSuccess?.targetRepetitions)

        val vachanSiddhi = list.filter { it.purposeGroup == "Vachan Siddhi" }
        assertEquals(5, vachanSiddhi.size)

        val fortune = list.filter { it.purposeGroup == "Fortune" }
        assertEquals(3, fortune.size)
    }

    @Test
    fun testJapaCounterProgress() = runBlocking {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = MantraViewModel(app)

        val initialCount = viewModel.japaCount.value
        viewModel.onBeadTapped()
        assertEquals(initialCount + 1, viewModel.japaCount.value)
    }

    @Test
    fun testSearchFiltering() = runBlocking {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = MantraViewModel(app)

        viewModel.setSearchQuery("Wealth")
        assertEquals("Wealth", viewModel.searchQuery.value)
    }

    @Test
    fun testEditProfile() = runBlocking {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = MantraViewModel(app)

        val initialProfile = viewModel.profile.value
        assertNotNull(initialProfile)

        // Update profile with custom details
        viewModel.updateProfile(
            name = "Aarav Sharma",
            path = "Bhaktamara Stotra Sadhana",
            practiceTime = "Sunrise • Ushakal",
            avatarSymbol = "🪷",
            dailyGoal = 216,
            sankalpa = "Cultivating compassion and clarity through sacred sound."
        )

        val updatedProfile = viewModel.profile.value
        assertEquals("Aarav Sharma", updatedProfile.name)
        assertEquals("Bhaktamara Stotra Sadhana", updatedProfile.path)
        assertEquals("Sunrise • Ushakal", updatedProfile.practiceTime)
        assertEquals("🪷", updatedProfile.avatarSymbol)
        assertEquals(216, updatedProfile.dailyGoal)
        assertEquals("Cultivating compassion and clarity through sacred sound.", updatedProfile.sankalpa)

        // Reset to defaults
        viewModel.resetProfileToDefaults()
        val resetProfile = viewModel.profile.value
        assertEquals("Sadhaka Practitioner", resetProfile.name)
        assertEquals("Preksha Meditation & Japa Path", resetProfile.path)
        assertEquals("ॐ", resetProfile.avatarSymbol)
        assertEquals(108, resetProfile.dailyGoal)
    }
}

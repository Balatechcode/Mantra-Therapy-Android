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
import org.junit.Assert.assertFalse
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
        assertTrue("Seed mantras must not be empty", list.isNotEmpty())

        val attainingSuccess = list.find { it.id == "attaining_success" }
        assertNotNull(attainingSuccess)
        assertEquals("Mantra 01", attainingSuccess?.number)
        assertEquals(108, attainingSuccess?.targetRepetitions)
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
}

package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.example.data.MantraRepository
import com.example.data.PractitionerProfile
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MantraTherapyTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [34])
class GreetingScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splash_screenshot() {
        composeTestRule.setContent {
            MantraTherapyTheme {
                SplashScreen(onEnterApp = {})
            }
        }
        composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/splash.png")
    }

    @Test
    fun home_screen_renders() {
        composeTestRule.setContent {
            MantraTherapyTheme {
                HomeScreen(
                    allMantras = MantraRepository.seedMantras,
                    selectedPurposeChip = "All",
                    onSelectPurposeChip = {},
                    onSearchClick = {},
                    onViewAllCategories = {},
                    onSelectMantra = {},
                    onContinuePractice = {},
                    onOpenProfile = {},
                    practitionerAvatar = "ॐ",
                    practitionerName = "Sadhaka Practitioner"
                )
            }
        }
    }

    @Test
    fun profile_screen_and_edit_dialog_render() {
        composeTestRule.setContent {
            MantraTherapyTheme {
                ProfileScreen(
                    profile = PractitionerProfile(),
                    totalRepetitions = 2592,
                    totalSessions = 24,
                    vibrationEnabled = true,
                    onToggleVibration = {},
                    onSaveProfile = { _, _, _, _, _, _ -> },
                    onResetProfile = {}
                )
            }
        }

        // Click Edit Profile button to open dialog
        composeTestRule.onNodeWithTag("edit_profile_button").performClick()
        composeTestRule.onNodeWithTag("edit_profile_dialog").assertExists()
        composeTestRule.onNodeWithTag("edit_profile_name_input").assertExists()
        composeTestRule.onNodeWithTag("edit_profile_save_button").assertExists()
    }
}

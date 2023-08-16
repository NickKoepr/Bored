package nl.nickkoepr.bored.mainScreen

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.dummy.network.DummyNetworkRepository
import nl.nickkoepr.bored.ui.screens.main.BoredMainScreen
import nl.nickkoepr.bored.ui.screens.main.BoredMainViewModel
import nl.nickkoepr.bored.ui.windowSize.WindowSize
import nl.nickkoepr.bored.utils.toPercent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initScreen() {
        composeTestRule.setContent {
            BoredMainScreen(
                windowSize = WindowSize.COMPACT,
                viewModel = BoredMainViewModel(DummyNetworkRepository())
            )
        }
    }

    @Test
    fun mainScreen_activityString_activityIsDisplayedOnScreen() {
        val displayedDummyActivity = DummyActivity.activity1
        composeTestRule.onNodeWithText(displayedDummyActivity.activity).assertIsDisplayed()
    }

    @Test
    fun mainScreen_participants_participantsIsDisplayedOnScreen() {
        val displayedDummyActivity = DummyActivity.activity1
        composeTestRule.onNodeWithText(displayedDummyActivity.participants.toString())
            .assertIsDisplayed()
    }

    @Test
    fun mainScreen_price_priceIsDisplayedOnScreen() {
        val displayedDummyActivity = DummyActivity.activity1
        composeTestRule.onNodeWithText(displayedDummyActivity.price.toPercent()).assertIsDisplayed()
    }

    @Test
    fun mainScreen_accessibility_accessibilityIsDisplayedOnScreen() {
        val displayedDummyActivity = DummyActivity.activity1
        composeTestRule.onNodeWithText(displayedDummyActivity.accessibility.toPercent())
            .assertIsDisplayed()
    }

    @Test
    fun mainScreen_label_labelIsDisplayedOnScreen() {
        val displayedDummyActivity = DummyActivity.activity1
        val type = ApplicationProvider.getApplicationContext<Context>()
            .getString(displayedDummyActivity.type.label)
        composeTestRule.onNodeWithText(type).assertIsDisplayed()
    }

    @Test
    fun mainScreen_generateActivityButton_generateNewActivity() {
        val displayedDummyActivity = DummyActivity.activity2
        composeTestRule.onNodeWithTag("generateActivityFab").performClick()
        composeTestRule.onNodeWithText(displayedDummyActivity.activity).assertIsDisplayed()
    }
}

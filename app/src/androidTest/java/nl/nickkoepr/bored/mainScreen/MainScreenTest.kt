package nl.nickkoepr.bored.mainScreen

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.dummy.repositories.DummyDatabaseRepository
import nl.nickkoepr.bored.dummy.repositories.DummyNetworkRepository
import nl.nickkoepr.bored.ui.screens.main.BoredMainScreen
import nl.nickkoepr.bored.ui.screens.main.BoredMainViewModel
import nl.nickkoepr.bored.ui.windowSize.WindowSize
import nl.nickkoepr.bored.utils.toPercent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Tests created for testing the MainScreen composables.
 */
class MainScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var dummyDatabaseRepository: DatabaseRepository

    @Before
    fun initScreen() {
        dummyDatabaseRepository = DummyDatabaseRepository()
        composeTestRule.setContent {
            BoredMainScreen(
                windowSize = WindowSize.COMPACT,
                viewModel = BoredMainViewModel(DummyNetworkRepository(), dummyDatabaseRepository),
                displaySnackbar = { }
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun mainScreen_favoriteActivityButton_addFavoriteActivity() = runTest {
        val favoriteIconButton = composeTestRule.onNodeWithTag("favoriteStarIconButton")
        favoriteIconButton.assertIsDisplayed()
        favoriteIconButton.performClick()
        val result = dummyDatabaseRepository.getFavorites().first()
        assertFalse(result.isEmpty())
        assertEquals(DummyActivity.activity1.key, result[0].key)
    }
}

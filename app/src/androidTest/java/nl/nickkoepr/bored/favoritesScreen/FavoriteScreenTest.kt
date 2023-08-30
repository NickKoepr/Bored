package nl.nickkoepr.bored.favoritesScreen

/**
 * Tests created for testing the FavoriteScreen composables.
 */
import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.dummy.repositories.DummyDatabaseRepository
import nl.nickkoepr.bored.ui.screens.favorites.FavoritesScreen
import nl.nickkoepr.bored.ui.screens.favorites.FavoritesViewModel
import nl.nickkoepr.bored.utils.toPercent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import nl.nickkoepr.bored.R

class FavoriteScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var dummyDatabase: DatabaseRepository

    @Before
    fun initScreen() {
        dummyDatabase = DummyDatabaseRepository()
        composeTestRule.setContent {
            FavoritesScreen({}, viewModel = FavoritesViewModel(dummyDatabase))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoriteScreen_favoriteCard_isCardDisplayedWithInformation() = runTest {
        val activity = DummyActivity.activity1
        val type = ApplicationProvider.getApplicationContext<Context>()
            .getString(activity.type.label)
        dummyDatabase.addFavorite(activity)

        // The [icon] at the end of the activity is a placeholder for the favorite star icon.
        composeTestRule.onNodeWithText("${activity.activity}[icon]").assertIsDisplayed()

        composeTestRule.onNodeWithText(activity.price.toPercent()).assertIsDisplayed()
        composeTestRule.onNodeWithText(type).assertIsDisplayed()
        composeTestRule.onNodeWithText(activity.accessibility.toPercent()).assertIsDisplayed()
        composeTestRule.onNodeWithText(activity.participants.toString()).assertIsDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoriteScreen_favoriteCard_expandedCardInformation() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val activity = DummyActivity.activity1
        dummyDatabase.addFavorite(activity)

        composeTestRule.onNodeWithText("${activity.activity}[icon]").performClick()
        // Those four strings are displayed when the activity favorite card is expanded. Otherwise
        // only the stats and icons are displayed.
        composeTestRule.onNodeWithText(context.getString(R.string.accessibility)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.participants)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.price)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.type)).assertIsDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun favoriteScreen_favoriteCard_removeCardWhenPressingOnStar() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val activity = DummyActivity.activity1
        dummyDatabase.addFavorite(activity)

        composeTestRule.onNodeWithTag("favoriteStarIconButton").performClick()
        // Check if the No favorites text is displayed on the screen.
        // When this text is displayed, then there are no more activities on screen.
        composeTestRule.onNodeWithText(context.getString(R.string.no_favorites)).assertIsDisplayed()
    }
}
package nl.nickkoepr.bored.ui.screens.favorites

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.intents.openLink
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.DummyActivities
import nl.nickkoepr.bored.ui.ViewModelProvider
import nl.nickkoepr.bored.ui.screens.SelectedFilter
import nl.nickkoepr.bored.ui.screens.main.ActivityStats
import nl.nickkoepr.bored.ui.screens.main.BoredActivityText
import nl.nickkoepr.bored.ui.screens.main.LinkButton
import nl.nickkoepr.bored.utils.toPercent

@Composable
fun FavoritesScreen(
    displaySnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val favoriteActivityList by viewModel.getFavoriteActivities()
        .collectAsState(initial = emptyList())

    if (favoriteActivityList.isEmpty()) {
        NoFavoriteMessage(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(modifier = modifier) {
            items(favoriteActivityList) { activity ->
                val coroutineScore = rememberCoroutineScope()
                val removedFavoriteActivityString =
                    stringResource(id = R.string.removed_favorite_activity)
                FavoriteCard(
                    activity = activity,
                    favoriteSelected = true,
                    onFavoriteClick = {
                        coroutineScore.launch {
                            viewModel.removeFavoriteActivity(activity)
                            displaySnackbar(removedFavoriteActivityString)
                        }
                    },
                    modifier = Modifier.padding(bottom = 15.dp, start = 12.dp, end = 12.dp)
                )
            }
        }
    }
}

/**
 * Card that displays the given activity title with the activity stats (only the icon and the stat).
 * @param activity activity that has to be displayed on the card.
 */
@Composable
fun FavoriteCard(
    activity: Activity,
    favoriteSelected: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        modifier = modifier.clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 10.dp,
                bottom = 10.dp
            )
        ) {
            BoredFavoriteActivityText(
                activity = activity,
                favoriteSelected = favoriteSelected,
                onFavoriteClick = onFavoriteClick
            )
            // Add a link button when a activity contains a link.
            if (activity.link.isNotEmpty()) {
                LinkButton(onClick = { openLink(context, activity.link) })
            }
            Divider(
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(top = 14.dp, bottom = 10.dp)
            )
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    // Slide the new content in and the old content out. The expanded content has
                    // to slide out via the bottom, and the new content from the top. This is
                    // This also happens when the content is not expanded (but then the other way
                    // around).
                    slideInVertically(
                        initialOffsetY = { fullHeight -> if (expanded) fullHeight else -fullHeight },
                        animationSpec = tween(durationMillis = 150)
                    ) togetherWith slideOutVertically(
                        targetOffsetY = { fullHeight -> if (expanded) -fullHeight else fullHeight },
                        animationSpec = tween(durationMillis = 150)
                    )
                },
                label = "FavoriteExpandedAnimation"
            ) { isExpanded ->
                if (isExpanded) {
                    ActivityStats(activity = activity)
                } else {
                    ActivityStatsSmallOverview(activity = activity)
                }
            }
        }
    }
}

/**
 * Row containing all the different activity stats with an icon and the given stat.
 * @param activity activity whose status should be shown.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActivityStatsSmallOverview(activity: Activity, modifier: Modifier = Modifier) {
    FlowRow(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.ACCESSIBILITY,
            value = activity.accessibility.toPercent()
        )
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.PRICE,
            value = activity.price.toPercent()
        )
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.PARTICIPANTS,
            value = activity.participants.toString()
        )
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.TYPE,
            value = stringResource(id = activity.type.label)
        )
    }
}

/**
 * Stat item that displays the given selectedFilter icon and the given value.
 * @param selectedFilter the type of filter that will be shown on the stat item.
 * @param value value of the selectedFilter type.
 */
@Composable
fun ActivityStatsSmallItem(
    selectedFilter: SelectedFilter,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = selectedFilter.filterIcon),
            contentDescription = stringResource(
                id = selectedFilter.filterName
            ),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Display text for the favorite activity title.
 * @param activity the activity whose title has to be displayed.
 * @param favoriteSelected true if the activity is selected as favorite, otherwise false.
 * @param onFavoriteClick function runs when a user clicks on the star icon button. This function
 * also gives a boolean to indicate if the activity is added to the favorite list (true) or
 * removed (false) from this list.
 */
@Composable
fun BoredFavoriteActivityText(
    activity: Activity,
    favoriteSelected: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BoredActivityText(
        modifier = modifier,
        activity = activity,
        favoriteSelected = favoriteSelected,
        onFavoriteClick = onFavoriteClick,
        style = MaterialTheme.typography.displayMedium
    )
}

/**
 * Item that displays a no favorites message when a user has no activities in his favorite
 * list.
 */
@Composable
fun NoFavoriteMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(70.dp),
            painter = painterResource(id = R.drawable.star_full),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = R.string.no_favorites),
            style = MaterialTheme.typography.displaySmall,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoFavoriteMessagePreview() {
    NoFavoriteMessage()
}

@Preview(showBackground = true)
@Composable
fun FavoriteCardPreview() {
    FavoriteCard(
        activity = DummyActivities.activities[0],
        favoriteSelected = false,
        onFavoriteClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ActivityStatsSmallOverviewPreview() {
    ActivityStatsSmallOverview(activity = DummyActivities.activities[0])
}

@Preview(showBackground = true)
@Composable
fun ActivityStatsSmallItemPreview() {
    ActivityStatsSmallItem(selectedFilter = SelectedFilter.ACCESSIBILITY, "50%")
}
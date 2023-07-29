package nl.nickkoepr.bored.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.DummyActivities
import nl.nickkoepr.bored.network.Status
import nl.nickkoepr.bored.ui.ViewModelProvider
import nl.nickkoepr.bored.utils.toPercent

@Composable
fun BoredMainScreen(
    modifier: Modifier = Modifier,
    viewModel: BoredMainViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = modifier) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ActivityFilterList(
                selectedFilters = listOf(SelectedFilter.ACCESSIBILITY),
                onClick = {},
                modifier = Modifier.padding(bottom = 15.dp)
            )
            when (val status = uiState.status) {
                is Status.Success -> {
                    BoredActivityText(activity = status.activity)
                    Divider(
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier.padding(top = 40.dp, bottom = 25.dp)
                    )
                    ActivityStats(
                        activity = status.activity,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    )
                }

                is Status.Loading -> {
                    LoadingIndicator(modifier = Modifier.fillMaxWidth())
                }

                is Status.Error -> {
                    // TODO implement error
                }
            }
        }
        GenerateActivityFab(
            onClick = viewModel::generateActivity,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        )
    }
}

/**
 * Display activity text based on given activity.
 * @param activity activity that has to be displayed.
 */
@Composable
fun BoredActivityText(activity: Activity, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(end = 20.dp),
        text = activity.activity,
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

/**
 * Floating action button for receiving a new activity.
 * @param onClick function that runs when a user clicks on the FAB.
 */
@Composable
fun GenerateActivityFab(onClick: () -> Unit, modifier: Modifier = Modifier) {
    FloatingActionButton(modifier = modifier.size(70.dp), onClick = onClick) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.refresh),
            contentDescription = stringResource(id = R.string.refresh),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Row with all the possible filter chips.
 * @param selectedFilters list of all the filters that are active.
 * @param onClick runs when a user clicks on a filter chip.
 */
@Composable
fun ActivityFilterList(
    selectedFilters: List<SelectedFilter>,
    onClick: (SelectedFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(SelectedFilter.entries) { filter ->
            ActivityFilterChip(
                modifier = Modifier.padding(end = 10.dp),
                selected = selectedFilters.contains(filter),
                onClick = { onClick(filter) },
                label = filter.filterName,
                icon = filter.filterIcon
            )
        }
    }
}

/**
 * Chip for the activity filters.
 * @param selected true if the filter is active, otherwise false.
 * @param onClick runs when a user clicks on the filter chip.
 * @param label string resource that is displayed on the filter chip.
 * @param icon drawable resource that is displayed on the filter chip.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    @StringRes label: Int,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null
            )
        }
    )
}

/**
 * Get the ActivityStatItem for the given activity.
 * @param activity StatItem is generated from the given activity.
 */
@Composable
fun ActivityStats(activity: Activity, modifier: Modifier = Modifier) {
    @Composable
    fun ActivitySpacer() {
        Spacer(modifier = Modifier.height(15.dp))
    }

    Column(modifier = modifier.fillMaxWidth()) {
        ActivityStatItem(SelectedFilter.ACCESSIBILITY, activity.accessibility.toPercent())
        ActivitySpacer()
        ActivityStatItem(SelectedFilter.PRICE, activity.price.toPercent())
        ActivitySpacer()
        ActivityStatItem(SelectedFilter.PARTICIPANTS, activity.participants.toString())
        ActivitySpacer()
        ActivityStatItem(SelectedFilter.TYPE, stringResource(id = activity.type.label))
    }
}

/**
 * Make a ActivityStatItem based on the given information.
 * @param selectedFilter the activityStatItem is generated from this given filter.
 * @param dataDisplayText the text that is being displayed as the data for the selectedFilter.
 */
@Composable
fun ActivityStatItem(
    selectedFilter: SelectedFilter,
    dataDisplayText: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = selectedFilter.filterIcon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = dataDisplayText,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = stringResource(id = selectedFilter.filterName),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/**
 * Circular animation for indicating a loading request.
 */
@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun BoredActivityTextPreview() {
    BoredActivityText(DummyActivities.activities[0])
}

@Preview(showBackground = true)
@Composable
fun ActivityFilterChipPreview() {
    ActivityFilterChip(false, {}, R.string.accessibility, R.drawable.star_full)
}

@Preview(showBackground = true)
@Composable
fun ActivityStatsPreview() {
    ActivityStats(activity = DummyActivities.activities[0])
}

@Preview(showBackground = true)
@Composable
fun ActivityStatItemPreview() {
    ActivityStatItem(
        selectedFilter = SelectedFilter.ACCESSIBILITY,
        dataDisplayText = "100%"
    )
}
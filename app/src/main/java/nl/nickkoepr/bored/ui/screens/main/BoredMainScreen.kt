package nl.nickkoepr.bored.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.DummyActivities
import nl.nickkoepr.bored.network.Status
import nl.nickkoepr.bored.ui.ViewModelProvider

@Composable
fun BoredMainScreen(
    modifier: Modifier = Modifier,
    viewModel: BoredMainViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            // All dummy data currently :)
            ActivityFilterList(selectedFilters = listOf(SelectedFilter.ACCESSIBILITY), onClick = {})
            when (val status = uiState.status) {
                is Status.Success -> {
                    BoredActivityText(activity = status.activity)
                }
                is Status.Loading -> {
                    // TODO implement loading
                }
                is Status.Error -> {
                    // TODO implement error
                }
            }
        }
        GenerateActivityFab(
            onClick = { viewModel.generateActivity() },
            modifier = Modifier
                .align(Alignment.End)
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
package nl.nickkoepr.bored.ui.screens.main

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.intents.openLink
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.DummyActivities
import nl.nickkoepr.bored.network.Status
import nl.nickkoepr.bored.ui.ViewModelProvider
import nl.nickkoepr.bored.ui.windowSize.WindowSize
import nl.nickkoepr.bored.utils.toComma
import nl.nickkoepr.bored.utils.toPercent

@Composable
fun BoredMainScreen(
    windowSize: WindowSize,
    modifier: Modifier = Modifier,
    viewModel: BoredMainViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    // Used to communicate the selected filter from a callback to a composable function.
    var setFilter by rememberSaveable { mutableStateOf<SelectedFilter?>(null) }

    Box(modifier = modifier) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ActivityFilterList(
                selectedFilters = viewModel.getAllActiveFilters(),
                onClick = { selectedFilter ->
                    setFilter = selectedFilter
                },
                onRemoveClick = { selectedFilter ->
                    viewModel.resetFilter(selectedFilter)
                },
                arguments = uiState.arguments,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            when (val status = uiState.status) {
                is Status.Success -> {
                    BoredActivityText(activity = status.activity)
                    if (status.activity.link.isNotBlank()) {
                        val context = LocalContext.current
                        LinkButton(onClick = {
                            openLink(context, status.activity.link)
                        }, modifier = Modifier.padding(top = 8.dp))
                    }
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
                    ErrorIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
        GenerateActivityFab(
            onClick = viewModel::generateActivity,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
                .testTag("generateActivityFab")
        )
    }

    if (setFilter != null) {
        when (setFilter) {
            SelectedFilter.ACCESSIBILITY -> {
                FilterBottomSheetScaffold(
                    windowSize = windowSize,
                    selectedFilter = SelectedFilter.ACCESSIBILITY,
                    filterElement = {
                        val minAccessibility = uiState.arguments.minAccessibility.toPercent(0.0f)
                        val maxAccessibility = uiState.arguments.maxAccessibility.toPercent(100f)
                        FilterElementSliderZeroToHundred(
                            value = minAccessibility..maxAccessibility,
                            onValueChange = {
                                viewModel.updateArguments(
                                    uiState.arguments.copy(
                                        minAccessibility = it.start.toComma(),
                                        maxAccessibility = it.endInclusive.toComma()
                                    )
                                )

                            }
                        )
                    },
                    dismissRequest = { setFilter = null })
            }

            SelectedFilter.PRICE -> {
                FilterBottomSheetScaffold(
                    windowSize = windowSize,
                    selectedFilter = SelectedFilter.PRICE,
                    filterElement = {
                        val minPrice = uiState.arguments.minPrice.toPercent(0.0f)
                        val maxPrice = uiState.arguments.maxPrice.toPercent(100f)
                        FilterElementSliderZeroToHundred(
                            value = minPrice..maxPrice,
                            onValueChange = {
                                viewModel.updateArguments(
                                    uiState.arguments.copy(
                                        minPrice = it.start.toComma(),
                                        maxPrice = it.endInclusive.toComma()
                                    )
                                )
                            }
                        )
                    },
                    dismissRequest = { setFilter = null })
            }

            SelectedFilter.TYPE -> {
                FilterBottomSheetScaffold(
                    windowSize = windowSize,
                    selectedFilter = SelectedFilter.TYPE,
                    filterElement = {
                        FilterTypeList(
                            selectedType = uiState.arguments.type,
                            onTypeSelected = { type ->
                                viewModel.updateArguments(
                                    uiState.arguments.copy(
                                        type = type
                                    )
                                )
                            })
                    },
                    dismissRequest = { setFilter = null })
            }

            SelectedFilter.PARTICIPANTS -> {
                FilterBottomSheetScaffold(
                    windowSize = windowSize,
                    selectedFilter = SelectedFilter.PARTICIPANTS,
                    filterElement = {
                        FilterElementSlider(
                            value = uiState.arguments.participants?.toFloat() ?: 0f,
                            range = 1f..5f,
                            onValueChange = {
                                viewModel.updateArguments(
                                    uiState.arguments.copy(
                                        participants = it.toInt()
                                    )
                                )
                            },
                            steps = 3
                        )
                    },
                    dismissRequest = { setFilter = null })
            }

            else -> {}
        }
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

/**
 * Sync problem icon indicating a potentially error while making a request.
 */
@Composable
fun ErrorIndicator(modifier: Modifier = Modifier) {
    Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
        Icon(
            modifier = Modifier.size(60.dp),
            painter = painterResource(id = R.drawable.sync_problem),
            contentDescription = stringResource(id = R.string.sync_error),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Button that can be showed when a user is able to open a link.
 * @param onClick function that runs when a user clicks on this button
 */
@Composable
fun LinkButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text = stringResource(id = R.string.open_link))
    }
}

@Preview(showBackground = true)
@Composable
fun LinkButtonPreview() {
    LinkButton(onClick = {})
}

@Preview(showBackground = true)
@Composable
fun BoredActivityTextPreview() {
    BoredActivityText(DummyActivities.activities[0])
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
package nl.nickkoepr.bored.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.model.Type

/**
 * Row with all the possible filter chips.
 * @param selectedFilters list of all the filters that are active.
 * @param onClick runs when a user clicks on a filter chip.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFilterList(
    selectedFilters: List<SelectedFilter>,
    onClick: (SelectedFilter) -> Unit,
    onRemoveClick: (SelectedFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier) {
        items(SelectedFilter.entries) { filter ->
            val selectedFilter = selectedFilters.contains(filter)
            FilterChip(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .height(38.dp),
                selected = selectedFilter,
                onClick = { onClick(filter) },
                label = { Text(text = stringResource(id = filter.filterName)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = filter.filterIcon),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (selectedFilter) {
                        Icon(
                            modifier = Modifier.clickable { onRemoveClick(filter) },
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = stringResource(id = R.string.remove_filter)
                        )
                    }
                }
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
 * Show a bottom sheet to the user that contains the given content for filtering for the given
 * selectedFilter.
 * @param selectedFilter the selected filter. The bottom sheet is based on the given selectedFilter.
 * @param filterElement composable function('s) that are shown when the filter opens.
 * @param dismissRequest function that is called when a user leaves the filter bottom sheet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheetScaffold(
    selectedFilter: SelectedFilter,
    filterElement: @Composable () -> Unit,
    dismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(modifier = modifier, onDismissRequest = dismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 80.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = selectedFilter.filterIcon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(id = selectedFilter.filterName),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            filterElement()
        }
    }
}

/**
 * Filter range slider with a given range.
 * @param value range of the current selected values.
 * @param range max range of the slider.
 * @param steps number of steps the slider makes when a user drags the slider around.
 * @param onValueChange function that is called when the slider value changes.
 * @param isPercent true if the range value is a percent value (this will show a trailing %),
 * default false.
 */
@Composable
fun FilterElementRangeSlider(
    value: ClosedFloatingPointRange<Float>,
    range: ClosedFloatingPointRange<Float>,
    steps: Int,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    isPercent: Boolean = false
) {
    Column(modifier = modifier) {
        RangeSlider(
            value = value,
            valueRange = range,
            onValueChange = onValueChange,
            steps = steps
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = range.start.toInt().toString() + if (isPercent) "%" else "")
            Text(text = range.endInclusive.toInt().toString() + if (isPercent) "%" else "")
        }
    }
}

/**
 * Slider for selecting a specific value in the given range.
 * @param value the current selected value.
 * @param range max range of the slider.
 * @param steps number of steps the slider makes when a user drags the slider around.
 * @param onValueChange function that is called when the slider value changes.
 */
@Composable
fun FilterElementSlider(
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    steps: Int,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Slider(
            value = value,
            valueRange = range,
            onValueChange = onValueChange,
            modifier = modifier,
            steps = steps
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = range.start.toInt().toString())
            Text(text = range.endInclusive.toInt().toString())
        }
    }
}

/**
 * [FilterElementRangeSlider] with a range from 0 to 100 and steps of 10.
 * @param value currently selected value range.
 * @param onValueChange called when the selected range is changed.
 */
@Composable
fun FilterElementSliderZeroToHundred(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterElementRangeSlider(
        value = value,
        range = 0f..100f,
        steps = 10,
        onValueChange = onValueChange,
        isPercent = true,
        modifier = modifier
    )
}

/**
 * Makes a list with all the possible [Type] values.
 * @param selectedType currently selected type (or null if no type is selected).
 * @param onTypeSelected function that is executed when a user selects a type.
 */
@Composable
fun FilterTypeList(
    selectedType: Type?,
    onTypeSelected: (Type) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(Type.entries) { type ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 5.dp)
                    .clickable {
                        onTypeSelected(type)
                    },
                colors = CardDefaults.cardColors(containerColor = if (selectedType != null && selectedType == type) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
            ) {
                Text(
                    text = stringResource(id = type.label),
                    modifier = Modifier.padding(start = 5.dp, top = 20.dp, bottom = 20.dp),
                    color = if (selectedType != null && selectedType == type) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
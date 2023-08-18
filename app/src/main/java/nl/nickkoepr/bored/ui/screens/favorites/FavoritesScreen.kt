package nl.nickkoepr.bored.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.DummyActivities
import nl.nickkoepr.bored.ui.ViewModelProvider
import nl.nickkoepr.bored.ui.screens.SelectedFilter
import nl.nickkoepr.bored.ui.screens.main.BoredActivityText
import nl.nickkoepr.bored.utils.toPercent

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    LazyColumn(modifier = modifier) {
        items(DummyActivities.activities) { activity ->
            FavoriteCard(activity = activity, modifier = Modifier.padding(bottom = 15.dp))
        }
    }
}

@Composable
fun FavoriteCard(activity: Activity, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 10.dp,
                bottom = 10.dp
            )
        ) {
            BoredActivityText(
                activity = activity,
                textStyle = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            ActivityStatsSmallOverview(activity = activity)
        }
    }
}

@Composable
fun ActivityStatsSmallOverview(activity: Activity, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.ACCESSIBILITY,
            value = activity.accessibility.toPercent()
        )
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.PARTICIPANTS,
            value = activity.participants.toString()
        )
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.PRICE,
            value = activity.price.toPercent()
        )
        ActivityStatsSmallItem(
            selectedFilter = SelectedFilter.TYPE,
            value = stringResource(id = activity.type.label)
        )
    }
}

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
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteCardPreview() {
    FavoriteCard(activity = DummyActivities.activities[0])
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
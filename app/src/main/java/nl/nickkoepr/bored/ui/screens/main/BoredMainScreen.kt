package nl.nickkoepr.bored.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.DummyActivities

@Composable
fun BoredMainScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        BoredActivityText(activity = DummyActivities.activities[0])
        GenerateActivityFab(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.End)
                .padding(15.dp)
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
        modifier = modifier,
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

@Preview(showBackground = true)
@Composable
fun BoredActivityTextPreview() {
    BoredActivityText(DummyActivities.activities[0])
}
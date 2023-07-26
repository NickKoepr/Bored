package nl.nickkoepr.bored.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.ui.screens.main.BoredMainScreen

@Composable
fun BoredApp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopBar(R.string.app_name, true, {}, {})
        },
        modifier = modifier
    ) { paddingValues ->
        BoredMainScreen(modifier = Modifier.padding(paddingValues).padding(start = 10.dp))
    }
}

/**
 * TopAppBar for the Bored app.
 * @param title The title to be displayed in the TopBar.
 * @param canNavigateBack True if a user can navigate to a previous screen.
 * @param onNavigateBack Function that runs when a user clicks on the navigate back icon.
 * @param moreOptionsClick Function that runs when a user clicks on the more options icon.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes title: Int,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    moreOptionsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = title))
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = stringResource(
                            id = R.string.back
                        )
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = moreOptionsClick) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vert),
                    contentDescription = stringResource(
                        id = R.string.more_options
                    )
                )
            }
        },
        // Maybe I will change these colors in the future.
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(R.string.app_name, true, {}, {})
}
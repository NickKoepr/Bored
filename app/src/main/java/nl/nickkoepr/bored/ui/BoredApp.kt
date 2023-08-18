package nl.nickkoepr.bored.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.ui.navigation.Screens
import nl.nickkoepr.bored.ui.screens.favorites.FavoritesScreen
import nl.nickkoepr.bored.ui.screens.main.BoredMainScreen
import nl.nickkoepr.bored.ui.windowSize.WindowSize

@Composable
fun BoredApp(
    windowSize: WindowSize,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val selectedScreen =
        Screens.valueOf(backStackEntry?.destination?.route ?: "HOME")

    Scaffold(
        topBar = {
            TopBar(R.string.app_name, false, {}, {})
        },
        bottomBar = {
            if (windowSize != WindowSize.EXPANDED) {
                BottomBar(selectedScreen, { screen ->
                    navController.popBackStack()
                    navController.navigate(screen.name)
                })
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Row(modifier = Modifier.padding(paddingValues)) {
            if (windowSize == WindowSize.EXPANDED) {
                SideNavigationRail(selected = selectedScreen, onSelect = { screen ->
                    navController.popBackStack()
                    navController.navigate(screen.name)
                })
            }
            NavHost(navController = navController, startDestination = Screens.HOME.name) {
                composable(route = Screens.HOME.name) {
                    BoredMainScreen(
                        windowSize = windowSize,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    )
                }
                composable(route = Screens.FAVOURITES.name) {
                    FavoritesScreen(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
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

/**
 * Bottom bar for the Bored app. Contains the Screens elements Home and Favourite.
 * @param selected current selected screen.
 * @param onSelect function that runs when a user makes a selection.
 */
@Composable
fun BottomBar(
    selected: Screens,
    onSelect: (Screens) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        Screens.entries.forEach { selectedScreen ->
            NavigationBarItem(
                selected = selectedScreen == selected,
                onClick = { onSelect(selectedScreen) },
                icon = {
                    Icon(
                        painter = painterResource(id = selectedScreen.navigationIcon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = selectedScreen.navigationName),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}

@Composable
fun SideNavigationRail(
    selected: Screens,
    onSelect: (Screens) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier) {
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Screens.entries.forEach { selectedScreen ->
                NavigationRailItem(
                    modifier = Modifier.padding(top = 10.dp),
                    selected = selected == selectedScreen,
                    onClick = { onSelect(selectedScreen) },
                    icon = {
                        Icon(
                            painter = painterResource(id = selectedScreen.navigationIcon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = selectedScreen.navigationName))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(R.string.app_name, true, {}, {})
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(Screens.HOME, { })
}
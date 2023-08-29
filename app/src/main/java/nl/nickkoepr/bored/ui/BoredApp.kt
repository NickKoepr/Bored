package nl.nickkoepr.bored.ui

import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.coroutines.launch
import nl.nickkoepr.bored.R
import nl.nickkoepr.bored.intents.LinkList
import nl.nickkoepr.bored.intents.openLink
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

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Source:
    //https://github.com/android/nowinandroid/blob/7945a2beba2e2eff8f081742e34f1b0378cd6c9c/app/src/main/java/com/google/samples/apps/nowinandroid/ui/NiaAppState.kt#L143C1-L157C14 (finally understand this =D)
    val navOptions = navOptions {
        popUpTo(Screens.HOME.name) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            var showAboutDialog by rememberSaveable { mutableStateOf(false) }

            TopBar(R.string.app_name, {
                showAboutDialog = true
            })
            if (showAboutDialog) {
                AboutDialog(onDismiss = { showAboutDialog = false })
            }
        },
        bottomBar = {
            if (windowSize != WindowSize.EXPANDED) {
                BottomBar(selectedScreen, { screen ->
                    navController.navigate(screen.name, navOptions)
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
                            .fillMaxSize(),
                        displaySnackbar = { message ->
                            coroutineScope.launch {
                                displaySnackbar(snackbarHostState, message)
                            }
                        }
                    )
                }
                composable(route = Screens.FAVOURITES.name) {
                    FavoritesScreen(
                        displaySnackbar = { message ->
                            coroutineScope.launch {
                                displaySnackbar(snackbarHostState, message)
                            }
                        },
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
 * @param onAboutClick runs when a user clicks on the extra about button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes title: Int,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isMoreOptionsExpanded by rememberSaveable { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = title))
        },
        actions = {
            IconButton(onClick = { isMoreOptionsExpanded = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.more_vert),
                    contentDescription = stringResource(
                        id = R.string.more_options
                    )
                )
                if (isMoreOptionsExpanded) {
                    MoreTopBarOptions(
                        onAboutClick = {
                            onAboutClick()
                            isMoreOptionsExpanded = false
                        },
                        onDismiss = { isMoreOptionsExpanded = false }
                    )
                }
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
 * Dialog that displays information about the project, containing links to informative pages about
 * regarding project.
 * @param onDismiss runs when the dialog has to close.
 */
@Composable
fun AboutDialog(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(330.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lightbulb),
                    contentDescription = null,
                    modifier = Modifier.size(59.dp)
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.created_by_dialog),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )
                Divider(
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(top = 20.dp)
                )
                LazyHorizontalGrid(
                    modifier = Modifier.height(40.dp),
                    rows = GridCells.Adaptive(100.dp)
                ) {
                    items(LinkList.entries) { linkList ->
                        TextButton(
                            modifier = Modifier.wrapContentSize(),
                            onClick = { openLink(context, linkList) }) {
                            Text(
                                text = stringResource(id = linkList.id),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                TextButton(onClick = {
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }) {
                    Text(
                        text = stringResource(id = R.string.open_source_licenses),
                        fontWeight = FontWeight.Bold
                    )
                }

                TextButton(onClick = onDismiss) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.close),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

/**
 * DropdownMenu with an about option.
 * @param onAboutClick runs when a user clicks on the 'about' dropdown item.
 * @param onDismiss runs when a user clicks outside of the dropdown menu.
 */
@Composable
fun MoreTopBarOptions(
    onAboutClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        modifier = modifier
            .clickable { onAboutClick() }
            .padding(start = 35.dp, end = 35.dp, top = 2.dp, bottom = 2.dp),
        expanded = true,
        onDismissRequest = onDismiss
    ) {
        Text(
            text = stringResource(id = R.string.about),
            style = MaterialTheme.typography.labelMedium,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
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

private suspend fun displaySnackbar(snackbarHostState: SnackbarHostState, message: String) {
    snackbarHostState.currentSnackbarData?.dismiss()
    snackbarHostState.showSnackbar(
        message = message,
        duration = SnackbarDuration.Short,
        withDismissAction = true
    )
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(R.string.app_name, {})
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(Screens.HOME, { })
}
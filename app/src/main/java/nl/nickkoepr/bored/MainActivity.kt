package nl.nickkoepr.bored

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nl.nickkoepr.bored.ui.BoredApp
import nl.nickkoepr.bored.ui.theme.BoredTheme
import androidx.compose.ui.unit.dp
import nl.nickkoepr.bored.ui.windowSize.windowSizeClassToWindowSize

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoredTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChangeSystemBarColors(
                        navigationBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            3.dp
                        ),
                        statusBarColor = MaterialTheme.colorScheme.surface
                    )
                    val windowSize = calculateWindowSizeClass(activity = this)
                    BoredApp(windowSizeClassToWindowSize(windowSize))
                }
            }
        }
    }
}

/**
 * Change the navigation bar and the status bar colors of the app to a given color.
 * @param navigationBarColor [Color] of the navigationBar
 * @param statusBarColor [Color] of the statusBar
 */
@Composable
fun ChangeSystemBarColors(
    navigationBarColor: Color,
    statusBarColor: Color
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setNavigationBarColor(navigationBarColor, useDarkIcons)
        systemUiController.setStatusBarColor(statusBarColor)
    }
}
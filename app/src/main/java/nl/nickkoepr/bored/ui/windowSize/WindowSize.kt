package nl.nickkoepr.bored.ui.windowSize

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Compact
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Expanded
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Medium

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED
}

fun windowSizeClassToWindowSize(windowSizeClass: WindowSizeClass): WindowSize {
    when (windowSizeClass.widthSizeClass) {
        Compact -> return WindowSize.COMPACT
        Medium -> return WindowSize.MEDIUM
        Expanded -> return WindowSize.EXPANDED
        else -> return WindowSize.COMPACT
    }
}
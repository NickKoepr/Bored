package nl.nickkoepr.bored.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.nickkoepr.bored.R

/**
 * Represent the current selected screen inside the app.
 * @param navigationName string resource for the screen name.
 * @param navigationIcon drawable resource for the screen icon.
 */
enum class Screens(
    @StringRes val navigationName: Int,
    @DrawableRes val navigationIcon: Int
) {
    HOME(R.string.home, R.drawable.home),
    FAVORITES(R.string.favorites, R.drawable.star_full)
}
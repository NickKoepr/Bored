package nl.nickkoepr.bored.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.nickkoepr.bored.R

enum class Screens(
    @StringRes val navigationName: Int,
    @DrawableRes val navigationIcon: Int
) {
    HOME(R.string.home, R.drawable.home),
    FAVOURITES(R.string.favourites, R.drawable.star_full)
}
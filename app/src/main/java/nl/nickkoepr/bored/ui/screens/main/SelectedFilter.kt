package nl.nickkoepr.bored.ui.screens.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.nickkoepr.bored.R

enum class SelectedFilter(@StringRes val filterName: Int, @DrawableRes val filterIcon: Int) {
    ACCESSIBILITY(R.string.accessibility, R.drawable.accessibility),
    PRICE(R.string.price, R.drawable.price),
    TYPE(R.string.type, R.drawable.type),
    PARTICIPANTS(R.string.participants, R.drawable.participants)
}
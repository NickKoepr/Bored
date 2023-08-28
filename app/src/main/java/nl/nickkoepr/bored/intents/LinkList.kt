package nl.nickkoepr.bored.intents

import androidx.annotation.StringRes
import nl.nickkoepr.bored.R

// Suppress unused because these items are not used individually (but in a list)
@Suppress("unused")
enum class LinkList(@StringRes val id: Int, val url: String) {
    NICKKOEPR(R.string.nickkoepr, "https://nickkoepr.nl/"),
    GITHUB(R.string.github, "https://github.com/NickKoepr/Bored"),
    BOREDAPI(R.string.boredapi, "https://www.boredapi.com/")
}
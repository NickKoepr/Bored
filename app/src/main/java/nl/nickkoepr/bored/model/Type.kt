package nl.nickkoepr.bored.model

import androidx.annotation.StringRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.nickkoepr.bored.R

@Serializable
enum class Type(@StringRes val label: Int) {
    @SerialName("education")
    EDUCATION(R.string.education),
    @SerialName("recreational")
    RECREATIONAL(R.string.recreational),
    @SerialName("social")
    SOCIAL(R.string.social),
    @SerialName("diy")
    DIY(R.string.diy),
    @SerialName("charity")
    CHARITY(R.string.charity),
    @SerialName("cooking")
    COOKING(R.string.cooking),
    @SerialName("relaxation")
    RELAXATION(R.string.relaxation),
    @SerialName("music")
    MUSIC(R.string.music),
    @SerialName("busywork")
    BUSYWORK(R.string.busywork)
}
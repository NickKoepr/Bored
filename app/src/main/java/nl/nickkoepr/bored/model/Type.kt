package nl.nickkoepr.bored.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Type {
    @SerialName("education")
    EDUCATION,
    @SerialName("recreational")
    RECREATIONAL,
    @SerialName("social")
    SOCIAL,
    @SerialName("diy")
    DIY,
    @SerialName("charity")
    CHARITY,
    @SerialName("cooking")
    COOKING,
    @SerialName("relaxation")
    RELAXATION,
    @SerialName("music")
    MUSIC,
    @SerialName("busywork")
    BUSYWORK
}
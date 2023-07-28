package nl.nickkoepr.bored.model

import kotlinx.serialization.Serializable

@Serializable
enum class Type {
    EDUCATION,
    RECREATIONAL,
    SOCIAL,
    DIY,
    CHARITY,
    COOKING,
    RELAXATION,
    MUSIC,
    BUSYWORK
}
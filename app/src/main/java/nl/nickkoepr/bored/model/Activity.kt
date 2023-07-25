package nl.nickkoepr.bored.model

data class Activity(
    val key: String,
    val activity: String,
    val accessibility: Double,
    val type: Type,
    val participants: Int,
    val price: Double,
    val link: String?
)

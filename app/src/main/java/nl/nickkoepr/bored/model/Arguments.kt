package nl.nickkoepr.bored.model

data class Arguments(
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val minAccessibility: Double? = null,
    val maxAccessibility: Double? = null,
    val participants: Int? = null,
    val type: Type? = null
)
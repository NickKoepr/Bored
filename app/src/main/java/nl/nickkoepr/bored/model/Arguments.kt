package nl.nickkoepr.bored.model

data class Arguments(
    val minPrice: Double = 0.0,
    val maxPrice: Double = 1.0,
    val minAccessibility: Double = 0.0,
    val maxAccessibility: Double = 1.0,
    val participants: Int? = null,
    val type: Type = Type.NONE
)
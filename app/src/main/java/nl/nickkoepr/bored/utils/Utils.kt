package nl.nickkoepr.bored.utils

import kotlin.math.round

fun Double.toPercent(): String {
    return "${(this * 100).toInt()}%"
}

fun Double?.toPercent(standardValue: Float): Float {
    return this?.times(100)?.toFloat() ?: standardValue
}

fun Float.toComma(): Double {
    return (round(this.toDouble()) / 100)
}

/**
 * Convert minValue and maxValue to a percent string (minvalue% / maxvalue%).
 * @param minValue minimum value for the percent String
 * @param maxValue maximum value for the percent String
 * @return min and max value converted to a percent String
 */
fun toPercentString(minValue: Double?, maxValue: Double?): String {
    return "${minValue.toPercent(0f).toInt()}% / ${maxValue.toPercent(100f).toInt()}%"
}
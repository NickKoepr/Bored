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
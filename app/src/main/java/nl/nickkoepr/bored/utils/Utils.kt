package nl.nickkoepr.bored.utils

fun Double.toPercent(): String {
    return "${(this * 100).toInt()}%"
}
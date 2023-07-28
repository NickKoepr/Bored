package nl.nickkoepr.bored.model

import androidx.annotation.FloatRange
import kotlinx.serialization.Serializable

/**
 * Represents an activity given by the Bored api.
 * @param key unique key representing the activity.
 * @param activity the given activity.
 * @param accessibility range between 0.0 and 1.0 representing the accessibility where 0
 * is the most accessible.
 * @param type type of the activity.
 * @param participants number of participants for the activity.
 * @param price range between 0.0 and 1.0 representing the price where 0 is free.
 * @param link optional link with more information about the activity.
 */
@Serializable
data class Activity(
    val key: String,
    val activity: String,
    @FloatRange(0.0, 1.0) val accessibility: Double,
    val type: Type,
    val participants: Int,
    @FloatRange(0.0, 1.0) val price: Double,
    val link: String
)

object DummyActivities {
    val activities = listOf(
        Activity("0", "Volunteer and help out at a senior center", 1.0, Type.BUSYWORK, 1, 1.0, ""),
        Activity("1", "Learn how to make an Alexa skill", 1.0, Type.BUSYWORK, 1, 1.0, ""),
        Activity("2", "Make a bucket list", 1.0, Type.BUSYWORK, 1, 1.0, ""),
        Activity("3", "test", 1.0, Type.BUSYWORK, 1, 1.0, "")
    )
}
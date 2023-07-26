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

object DummyActivities {
    val activities = listOf(
        Activity("0", "Volunteer and help out at a senior center", 1.0, Type.BUSYWORK, 1, 1.0, null),
        Activity("1", "Learn how to make an Alexa skill", 1.0, Type.BUSYWORK, 1, 1.0, null),
        Activity("2", "Make a bucket list", 1.0, Type.BUSYWORK, 1, 1.0, null),
        Activity("3", "test", 1.0, Type.BUSYWORK, 1, 1.0, null)
    )
}
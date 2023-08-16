package nl.nickkoepr.bored.dummy

import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Type

object DummyActivity {
    val activity1 = Activity(
        key = "8503795",
        activity = "Go to a local thrift shop",
        type = Type.RECREATIONAL,
        participants = 1,
        price = 0.1,
        accessibility = 0.2,
        link = ""
    )
    val activity2 = Activity(
        key = "8503795",
        activity = "Take a spontaneous road trip with some friends",
        type = Type.SOCIAL,
        participants = 4,
        price = 0.2,
        accessibility = 0.3,
        link = ""
    )
}
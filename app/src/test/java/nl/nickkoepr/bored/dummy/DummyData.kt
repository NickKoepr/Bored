package nl.nickkoepr.bored.dummy

import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments
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
}

object DummyArguments {
    val argument1 = Arguments(
        minPrice = 0.1,
        maxPrice = 0.2,
        minAccessibility = 0.2,
        maxAccessibility = 1.0,
        participants = 2,
        type = Type.RECREATIONAL
    )
    val argument2 = Arguments(
        participants = 1,
        type = Type.RECREATIONAL
    )
}
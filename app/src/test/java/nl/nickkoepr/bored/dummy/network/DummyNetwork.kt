package nl.nickkoepr.bored.dummy.network

import nl.nickkoepr.bored.data.network.repository.NetworkRepository
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

class DummyNetworkRepository : NetworkRepository {
    override suspend fun getRandomActivity(arguments: Arguments): Activity {
        return DummyActivity.activity1
    }
}
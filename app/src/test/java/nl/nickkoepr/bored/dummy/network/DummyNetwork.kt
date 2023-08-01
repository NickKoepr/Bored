package nl.nickkoepr.bored.dummy.network

import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments

class DummyNetworkRepository : NetworkRepository {
    override suspend fun getRandomActivity(arguments: Arguments): Activity {
        return DummyActivity.activity1
    }
}
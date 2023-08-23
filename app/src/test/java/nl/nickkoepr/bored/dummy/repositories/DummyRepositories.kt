package nl.nickkoepr.bored.dummy.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments

class DummyNetworkRepository : NetworkRepository {
    override suspend fun getRandomActivity(arguments: Arguments): Activity {
        return DummyActivity.activity1
    }
}

class DummyDatabaseRepository : DatabaseRepository {
    override suspend fun addFavorite(activity: Activity) {
    }

    override suspend fun removeFavorite(activity: Activity) {
    }

    override fun getFavorites(): Flow<List<Activity>> {
        return emptyFlow()
    }

    override fun getActivityByKey(key: String): Flow<Activity?> {
        return emptyFlow()
    }
}
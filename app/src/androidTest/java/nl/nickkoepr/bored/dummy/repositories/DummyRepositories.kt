package nl.nickkoepr.bored.dummy.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments

class DummyNetworkRepository : NetworkRepository {
    private var countsCalled = 0
    override suspend fun getRandomActivity(arguments: Arguments): Activity {
        return if (countsCalled == 0) {
            countsCalled++
            DummyActivity.activity1
        } else {
            DummyActivity.activity2
        }
    }
}

class DummyDatabaseRepository : DatabaseRepository {
    private val dummyDataList = mutableListOf<Activity>()
    override suspend fun addFavorite(activity: Activity) {
        dummyDataList.add(activity)
    }

    override suspend fun removeFavorite(activity: Activity) {
        dummyDataList.remove(activity)
    }

    override fun getFavorites(): Flow<List<Activity>> {
        return flowOf(dummyDataList)
    }

    override fun getActivityByKey(key: String): Flow<Activity?> {
        return flowOf(dummyDataList.find { it.key == key })
    }
}
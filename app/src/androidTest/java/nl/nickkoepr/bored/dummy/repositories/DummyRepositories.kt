package nl.nickkoepr.bored.dummy.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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
    private val dummyDataListState = MutableStateFlow<List<Activity>>(emptyList())

    override suspend fun addFavorite(activity: Activity) {
        dummyDataListState.update { state ->
            state + activity
        }
    }

    override suspend fun removeFavorite(activity: Activity) {
        dummyDataListState.update { state ->
            state.filterNot { it == activity }
        }
    }

    override fun getFavorites(): Flow<List<Activity>> {
        return dummyDataListState
    }

    override fun getActivityByKey(key: String): Flow<Activity?> {
        return dummyDataListState.map { activityList ->
            activityList.find { activity -> activity.key == key }
        }
    }
}
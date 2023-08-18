package nl.nickkoepr.bored.data.database

import kotlinx.coroutines.flow.Flow
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.model.Activity

class BoredDatabaseRepository(private val boredDao: BoredDao): DatabaseRepository {
    override suspend fun addFavorite(activity: Activity) = boredDao.addFavorite(activity)
    override suspend fun removeFavorite(activity: Activity) = boredDao.removeFavorite(activity)
    override fun getFavorites(): Flow<List<Activity>> = boredDao.getFavorites()
}
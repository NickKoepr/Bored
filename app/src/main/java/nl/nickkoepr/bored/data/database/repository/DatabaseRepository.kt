package nl.nickkoepr.bored.data.database.repository

import kotlinx.coroutines.flow.Flow
import nl.nickkoepr.bored.model.Activity

/**
 * Interface that defines all the functions to interact with the database.
 */
interface DatabaseRepository {
    /**
     * Add the given activity to the favorites list.
     * @param activity activity to add to the favorite list
     */
    suspend fun addFavorite(activity: Activity)

    /**
     * Remove the given activity from the favorites list.
     * @param activity activity to remove from the favorites list
     */
    suspend fun removeFavorite(activity: Activity)

    /**
     * Get a list of all the favorites.
     * @return [Flow] that contains a list of the users favorite activities.
     */
    fun getFavorites(): Flow<List<Activity>>

    /**
     * Receive a activity by his key. This flow will return null if no activity is found.
     * @param key the activity key.
     */
    fun getActivityByKey(key: String): Flow<Activity?>
}
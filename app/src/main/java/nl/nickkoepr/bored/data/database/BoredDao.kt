package nl.nickkoepr.bored.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import nl.nickkoepr.bored.model.Activity

@Dao
interface BoredDao {
    @Insert
    suspend fun addFavorite(activity: Activity)

    @Delete
    suspend fun removeFavorite(activity: Activity)

    @Query("SELECT * FROM activity")
    fun getFavorites(): Flow<List<Activity>>

    @Query("SELECT * FROM activity WHERE `key` = :key")
    fun getActivityByKey(key: String): Flow<Activity?>
}
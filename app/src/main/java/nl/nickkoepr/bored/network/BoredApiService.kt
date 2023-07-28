package nl.nickkoepr.bored.network

import nl.nickkoepr.bored.model.Activity
import retrofit2.http.GET
import retrofit2.http.Query

interface BoredApiService {
    @GET("activity")
    suspend fun getRandomActivity(
        @Query("minprice") minPrice: Double?,
        @Query("maxprice") maxPrice: Double?,
        @Query("minaccessibility") minAccessibility: Double?,
        @Query("maxaccessibility") maxAccessibility: Double?,
        @Query("participants") participants: Int?,
        @Query("type") type: String?
    ): Activity
}
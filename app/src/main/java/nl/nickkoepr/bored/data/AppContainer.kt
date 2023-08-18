package nl.nickkoepr.bored.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.nickkoepr.bored.data.database.BoredDatabase
import nl.nickkoepr.bored.data.database.BoredDatabaseRepository
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.data.network.BoredNetworkRepository
import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.network.BoredApiService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val networkRepository: NetworkRepository
    val databaseRepository: DatabaseRepository
}

class BoredAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://www.boredapi.com/api/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retroFitService: BoredApiService by lazy {
        retrofit.create(BoredApiService::class.java)
    }

    override val networkRepository: NetworkRepository by lazy {
        BoredNetworkRepository(retroFitService)
    }

    override val databaseRepository: DatabaseRepository by lazy {
        BoredDatabaseRepository(BoredDatabase.getDatabase(context).boredDao())
    }
}
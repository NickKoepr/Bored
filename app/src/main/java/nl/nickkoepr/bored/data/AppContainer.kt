package nl.nickkoepr.bored.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import nl.nickkoepr.bored.data.network.BoredNetworkRepository
import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.network.BoredApiService
import retrofit2.Retrofit

interface AppContainer {
    val networkRepository: NetworkRepository
}

class BoredAppContainer : AppContainer {
    private val baseUrl = "https://www.boredapi.com/api/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json"))
        .baseUrl(baseUrl)
        .build()

    private val retroFitService: BoredApiService by lazy {
        retrofit.create(BoredApiService::class.java)
    }

    override val networkRepository: NetworkRepository by lazy {
        BoredNetworkRepository(retroFitService)
    }
}
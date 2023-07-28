package nl.nickkoepr.bored.data.network

import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments
import nl.nickkoepr.bored.network.BoredApiService

class BoredNetworkRepository(private val boredApiService: BoredApiService) : NetworkRepository {
    override fun getRandomActivity(arguments: Arguments): Activity {
        return boredApiService.getRandomActivity(
            minPrice = arguments.minPrice,
            maxPrice = arguments.maxPrice,
            minAccessibility = arguments.minAccessibility,
            maxAccessibility = arguments.maxAccessibility,
            participants = arguments.participants,
            type = arguments.type?.name
        )
    }
}
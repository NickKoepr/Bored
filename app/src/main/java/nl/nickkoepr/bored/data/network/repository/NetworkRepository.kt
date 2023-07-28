package nl.nickkoepr.bored.data.network.repository

import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments

interface NetworkRepository {
    /**
     * Get a random activity based on the given arguments.
     * @param arguments object with all the given arguments.
     * @return [Activity] activity class
     */
    suspend fun getRandomActivity(arguments: Arguments): Activity
}
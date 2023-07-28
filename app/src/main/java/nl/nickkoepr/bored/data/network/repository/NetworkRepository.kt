package nl.nickkoepr.bored.data.network.repository

import nl.nickkoepr.bored.model.Activity
import nl.nickkoepr.bored.model.Arguments

interface NetworkRepository {
    fun getRandomActivity(arguments: Arguments): Activity
}
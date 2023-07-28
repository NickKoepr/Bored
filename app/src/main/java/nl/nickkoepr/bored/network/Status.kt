package nl.nickkoepr.bored.network

import nl.nickkoepr.bored.model.Activity

sealed interface Status {
    data object Error : Status
    data object Loading : Status
    data class Success(val activity: Activity) : Status
}
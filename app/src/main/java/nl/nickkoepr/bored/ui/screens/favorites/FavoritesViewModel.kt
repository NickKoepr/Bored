package nl.nickkoepr.bored.ui.screens.favorites

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import nl.nickkoepr.bored.data.database.repository.DatabaseRepository
import nl.nickkoepr.bored.model.Activity

class FavoritesViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {
    fun getFavoriteActivities(): Flow<List<Activity>> {
        return databaseRepository.getFavorites()
    }
}
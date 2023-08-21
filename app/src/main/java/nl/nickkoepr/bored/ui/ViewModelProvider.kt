package nl.nickkoepr.bored.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import nl.nickkoepr.bored.BoredApplication
import nl.nickkoepr.bored.data.AppContainer
import nl.nickkoepr.bored.ui.screens.favorites.FavoritesViewModel
import nl.nickkoepr.bored.ui.screens.main.BoredMainViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            BoredMainViewModel(
                getAppContainer().networkRepository,
                getAppContainer().databaseRepository
            )
        }
        initializer {
            FavoritesViewModel(getAppContainer().databaseRepository)
        }
    }
}

private fun CreationExtras.getAppContainer(): AppContainer {
    return (this[APPLICATION_KEY] as BoredApplication).appContainer
}
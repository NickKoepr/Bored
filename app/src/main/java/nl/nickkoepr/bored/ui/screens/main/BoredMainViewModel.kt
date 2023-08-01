package nl.nickkoepr.bored.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.nickkoepr.bored.data.network.repository.NetworkRepository
import nl.nickkoepr.bored.model.Arguments
import nl.nickkoepr.bored.network.Status
import retrofit2.HttpException
import java.io.IOException

class BoredMainViewModel(private val networkRepository: NetworkRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(BoredMainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        generateActivity()
    }

    /**
     * Update the arguments inside the uiState with the given arguments.
     * @param arguments the uiState will be updated with the given arguments.
     */
    fun updateArguments(arguments: Arguments) {
        _uiState.update { state -> state.copy(arguments = arguments) }
    }

    /**
     * Returns all currently active filters.
     * @return a list with all the currently selected filters.
     */
    fun getAllActiveFilters(): List<SelectedFilter> {
        val arguments = uiState.value.arguments
        val selectedFilterList = mutableListOf<SelectedFilter>()

        if (arguments.minPrice != null && arguments.maxPrice != null)
            selectedFilterList.add(SelectedFilter.PRICE)
        if (arguments.minAccessibility != null && arguments.maxAccessibility != null)
            selectedFilterList.add(SelectedFilter.ACCESSIBILITY)
        if (arguments.participants != null) selectedFilterList.add(SelectedFilter.PARTICIPANTS)
        if (arguments.type != null) selectedFilterList.add(SelectedFilter.TYPE)

        return selectedFilterList
    }

    /**
     * Reset the given filter.
     * @param selectedFilter filter that has to be reset.
     */
    fun resetFilter(selectedFilter: SelectedFilter) {
        _uiState.update { state ->
            when (selectedFilter) {
                SelectedFilter.ACCESSIBILITY -> {
                    state.copy(
                        arguments = state.arguments.copy(
                            minAccessibility = null,
                            maxAccessibility = null
                        )
                    )
                }

                SelectedFilter.PRICE -> {
                    state.copy(
                        arguments = state.arguments.copy(
                            minPrice = null,
                            maxPrice = null
                        )
                    )
                }

                SelectedFilter.TYPE -> {
                    state.copy(
                        arguments = state.arguments.copy(
                            type = null,
                        )
                    )
                }

                SelectedFilter.PARTICIPANTS -> {
                    state.copy(
                        arguments = state.arguments.copy(
                            participants = null
                        )
                    )
                }
            }
        }
    }

    /**
     * Generate a random activity from the bored api and update the uiState with the current status.
     */
    fun generateActivity() {
        _uiState.update { status -> status.copy(status = Status.Loading) }
        viewModelScope.launch {
            // Update the uiState by making a copy of the status object.
            // Pass the arguments from the uiState to the getRandomActivity() function.
            _uiState.update { status ->
                status.copy(
                    status = try {
                        Status.Success(networkRepository.getRandomActivity(status.arguments))
                    } catch (e: IOException) {
                        Status.Error
                    } catch (e: HttpException) {
                        Status.Error
                    } catch (e: Throwable) {
                        Status.Error
                    }
                )
            }
        }
    }
}

data class BoredMainUiState(
    val status: Status = Status.Loading,
    val arguments: Arguments = Arguments()
)
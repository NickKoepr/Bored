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

    fun updateArguments(arguments: Arguments) {
        _uiState.update { state -> state.copy(arguments = arguments) }
    }

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
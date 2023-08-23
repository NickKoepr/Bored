package nl.nickkoepr.bored.viewModel

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nl.nickkoepr.bored.dummy.DummyActivity
import nl.nickkoepr.bored.dummy.DummyArguments
import nl.nickkoepr.bored.dummy.repositories.DummyDatabaseRepository
import nl.nickkoepr.bored.dummy.repositories.DummyNetworkRepository
import nl.nickkoepr.bored.model.Arguments
import nl.nickkoepr.bored.network.Status
import nl.nickkoepr.bored.rules.TestDispatcherRule
import nl.nickkoepr.bored.ui.screens.main.BoredMainViewModel
import nl.nickkoepr.bored.ui.screens.SelectedFilter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class BoredMainViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()
    private lateinit var viewModel: BoredMainViewModel

    @Before
    fun init() {
        viewModel = BoredMainViewModel(DummyNetworkRepository(), DummyDatabaseRepository())
    }

    @Test
    fun boredMainViewModel_getRandomActivity_returnsSuccessWithActivity() {
        viewModel.generateActivity()
        val status = viewModel.uiState.value.status
        assertTrue(status is Status.Success)
        val successStatus = status as Status.Success
        assertEquals(DummyActivity.activity1, successStatus.activity)
    }

    @Test
    fun boredMainViewModel_getActiveFilters_returnsNoFilters() {
        // An empty Arguments object must return no active filers.
        viewModel.updateArguments(Arguments())
        val result = viewModel.getAllActiveFilters()
        assertTrue(result.isEmpty())
    }

    @Test
    fun boredMainViewModel_getActiveFilters_returnTwoFilters() {
        viewModel.updateArguments(DummyArguments.argument2)
        val result = viewModel.getAllActiveFilters()
        assertTrue(result.contains(SelectedFilter.PARTICIPANTS))
        assertTrue(result.contains(SelectedFilter.TYPE))
    }

    @Test
    fun boredMainViewModel_resetFiler_FilerDataIsNull() {
        viewModel.updateArguments(DummyArguments.argument1)
        viewModel.resetFilter(SelectedFilter.PARTICIPANTS)
        assertNull(viewModel.uiState.value.arguments.participants)

        viewModel.resetFilter(SelectedFilter.TYPE)
        assertNull(viewModel.uiState.value.arguments.type)

        viewModel.resetFilter(SelectedFilter.ACCESSIBILITY)
        assertNull(viewModel.uiState.value.arguments.maxAccessibility)
        assertNull(viewModel.uiState.value.arguments.minAccessibility)

        viewModel.resetFilter(SelectedFilter.PRICE)
        assertNull(viewModel.uiState.value.arguments.maxPrice)
        assertNull(viewModel.uiState.value.arguments.minPrice)
    }
}
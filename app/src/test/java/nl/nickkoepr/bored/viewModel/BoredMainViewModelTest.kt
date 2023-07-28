package nl.nickkoepr.bored.viewModel

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.nickkoepr.bored.dummy.network.DummyActivity
import nl.nickkoepr.bored.dummy.network.DummyNetworkRepository
import nl.nickkoepr.bored.network.Status
import nl.nickkoepr.bored.rules.TestDispatcherRule
import nl.nickkoepr.bored.ui.screens.main.BoredMainViewModel
import org.junit.Rule
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class BoredMainViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun boredMainViewModel_getRandomActivity_returnsSuccessWithActivity() = runTest {
        val viewModel = BoredMainViewModel(DummyNetworkRepository())
        viewModel.generateActivity()
        val status = viewModel.uiState.value.status
        assertTrue(status is Status.Success)
        val successStatus = status as Status.Success
        assertEquals(DummyActivity.activity1, successStatus.activity)
    }
}
package org.chapp.findfin.feature.home.presentation.ui.nearby.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult
import org.chapp.findfin.feature.home.domain.nearby.model.Service
import org.chapp.findfin.feature.home.domain.nearby.usecase.GetNearByServicesUseCase
import org.chapp.findfin.feature.home.presentation.ui.nearby.model.NearByUiState
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class NearByViewModelImplTest {
    private val getNearByServicesUseCase = mockk<GetNearByServicesUseCase>()

    @Test
    fun `uiState emits Service state when use case returns Location list`() {
        runTest {
            val mockLocationList = listOf<Service>(mockk(relaxed = true))
            val mockResult = NearByResult.Location(mockLocationList)
            coEvery { getNearByServicesUseCase() } returns mockResult

            val nearByViewModel = createNearByViewModel()
            nearByViewModel.getNearByServices()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe NearByUiState.Loading
                awaitItem().shouldBeInstanceOf<NearByUiState.Service>()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `uiState emits Empty state when use case returns empty Location list`() {
        runTest {
            val mockResult = NearByResult.Location(listOf())
            coEvery { getNearByServicesUseCase() } returns mockResult

            val nearByViewModel = createNearByViewModel()
            nearByViewModel.getNearByServices()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe NearByUiState.Loading
                awaitItem() shouldBe NearByUiState.Empty
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `uiState emits Error state when use case returns UnknownError`() {
        runTest {
            coEvery { getNearByServicesUseCase() } returns NearByResult.UnknownError

            val nearByViewModel = createNearByViewModel()
            nearByViewModel.getNearByServices()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe NearByUiState.Loading
                awaitItem() shouldBe NearByUiState.Error
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    private fun createNearByViewModel() =
        NearByViewModelImpl(
            getNearByBanks = getNearByServicesUseCase,
        )
}

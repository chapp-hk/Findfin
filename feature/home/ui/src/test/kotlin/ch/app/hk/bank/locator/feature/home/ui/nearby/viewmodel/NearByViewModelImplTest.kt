package ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.Service
import ch.app.hk.bank.locator.feature.home.domain.nearby.usecase.GetNearByServicesUseCase
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByError
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class NearByViewModelImplTest {
    private val getNearByServicesUseCase = mockk<GetNearByServicesUseCase>()

    @Test
    fun `getNearByServices emits Empty state when use case returns Location list`() {
        runTest {
            val mockLocationList = listOf<Service>(mockk(relaxed = true))
            val mockResult = NearByResult.Location(mockLocationList)
            coEvery { getNearByServicesUseCase() } returns mockResult

            val nearByViewModel = createNearByViewModel()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem()
                    .shouldBeInstanceOf<ScreenState.Success<NearByUiState.Service, NearByUiState.Error>>()
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `getNearByServices emits Empty state when use case returns empty Location list`() {
        runTest {
            val mockResult = NearByResult.Location(listOf())
            coEvery { getNearByServicesUseCase() } returns mockResult

            val nearByViewModel = createNearByViewModel()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Empty
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `getNearByServices emits Error state when use case returns PermissionNotGranted`() {
        runTest {
            coEvery { getNearByServicesUseCase() } returns NearByResult.PermissionNotGranted

            val nearByViewModel = createNearByViewModel()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Error(NearByUiState.Error(NearByError.PERMISSION_NOT_GRANTED))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `getNearByServices emits Error state when use case returns GpsNotSupported`() {
        runTest {
            coEvery { getNearByServicesUseCase() } returns NearByResult.GpsNotSupported

            val nearByViewModel = createNearByViewModel()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Error(NearByUiState.Error(NearByError.GPS_NOT_SUPPORTED))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `getNearByServices emits Error state when use case returns GpsIsOff`() {
        runTest {
            coEvery { getNearByServicesUseCase() } returns NearByResult.GpsIsOff

            val nearByViewModel = createNearByViewModel()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Error(NearByUiState.Error(NearByError.GPS_IS_OFF))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `getNearByServices emits Error state when use case returns UnknownError`() {
        runTest {
            coEvery { getNearByServicesUseCase() } returns NearByResult.UnknownError

            val nearByViewModel = createNearByViewModel()

            nearByViewModel.uiState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Error(NearByUiState.Error(NearByError.UNKNOWN_ERROR))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    private fun createNearByViewModel() = NearByViewModelImpl(getNearByServicesUseCase)
}

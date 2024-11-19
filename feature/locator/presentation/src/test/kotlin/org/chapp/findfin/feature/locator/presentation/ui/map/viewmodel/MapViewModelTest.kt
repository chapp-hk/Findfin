package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationModel
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("MapViewModel unit tests")
class MapViewModelTest {
    private val bankLocationRepository = mockk<BankLocationRepository>()

    @Test
    fun `uiState should emit empty list when repository returns empty list`() {
        runTest {
            // Arrange
            coEvery { bankLocationRepository.getAll() } returns emptyList()
            val viewModel = MapViewModel(bankLocationRepository)

            viewModel.uiState.test {
                awaitItem() shouldBe emptyList()
                cancelAndConsumeRemainingEvents() shouldBe emptyList()
            }
        }
    }

    @Test
    fun `uiState should emit mapped list when repository returns data`() {
        runTest {
            // Arrange
            val bankLocationModels =
                listOf(
                    BankLocationModel(
                        type = "type",
                        district = "district",
                        bankName = "bankName",
                        typeName = "typeName",
                        address = "New York, NY",
                        serviceHours = "serviceHours",
                        latitude = 40.7128,
                        longitude = -74.0060,
                    ),
                )
            coEvery { bankLocationRepository.getAll() } returns bankLocationModels
            val viewModel = MapViewModel(bankLocationRepository)

            viewModel.uiState.test {
                awaitItem() shouldBe emptyList()
                awaitItem().shouldBeInstanceOf<List<MapMarker>>().size shouldBe 1
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}

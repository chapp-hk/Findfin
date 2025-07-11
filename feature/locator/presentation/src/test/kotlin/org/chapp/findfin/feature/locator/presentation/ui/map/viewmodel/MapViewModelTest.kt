package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.core.map.PositionBounds
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("MapViewModel unit tests")
class MapViewModelTest {
    private val savedStateHandle = SavedStateHandle()
    private val bankRepository = mockk<BankRepository>()

    private val mapViewModel =
        MapViewModel(
            savedStateHandle = savedStateHandle,
            bankRepository = bankRepository,
        )

    @Test
    fun `getBanksWithinBound emits mapped markers`() {
        runTest {
            val mockBankModel =
                mockk<BankModel>(relaxed = true) {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                    every { address } returns "Test Address"
                    every { type } returns "ATM"
                }

            coEvery { bankRepository.getBanksByParameters(bound = any()) } returns
                listOf(mockBankModel)

            val bound =
                PositionBounds(
                    northeast = Position(latitude = 1.0, longitude = 2.0),
                    southwest = Position(latitude = 0.0, longitude = 1.0),
                )
            mapViewModel.getBanksWithinBound(bound)

            mapViewModel.uiState.test {
                awaitItem() shouldBe emptyList()
                awaitItem().let {
                    it.shouldBeInstanceOf<List<MapMarker<BankType>>>()
                    it.size shouldBe 1
                }
                ensureAllEventsConsumed()
            }
        }
    }
}

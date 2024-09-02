package ch.app.hk.bank.locator.feature.bank.ui.banklist.viewmodel

import app.cash.turbine.test
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.bank.data.repo.location.repository.BankLocationRepository
import ch.app.hk.bank.locator.testing.extension.MainDispatcherExtension
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("BankListViewModel unit tests")
class BankListViewModelImplTest {
    private val bankLocationRepository = mockk<BankLocationRepository>()

    @Test
    fun `test screenState should emit values from locatorRepository`() {
        runTest {
            coEvery { bankLocationRepository.getAllBanks() } returns listOf("Bank 1", "Bank 2", "Bank 3")

            val viewModel = createBankListViewModel()

            viewModel.screenState.test {
                awaitItem() shouldBe ScreenState.Loading
                awaitItem() shouldBe ScreenState.Success(listOf("Bank 1", "Bank 2", "Bank 3"))
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    private fun createBankListViewModel() = BankListViewModelImpl(bankLocationRepository)
}

package ch.app.hk.bank.locator.feature.bank.presentation.ui.banklist.viewmodel

import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.chapp.findfin.feature.bank.presentation.ui.banklist.viewmodel.BankListViewModelImpl
import org.chapp.findfin.testing.extension.MainDispatcherExtension
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
@DisplayName("BankListViewModel unit tests")
class BankListViewModelImplTest {
    private val bankRepository = mockk<BankRepository>()

    @Test
    fun `test screenState should emit values from locatorRepository`() {
        runTest {
            coEvery { bankRepository.getAllBanks() } returns listOf("Bank 1", "Bank 2", "Bank 3")

            val viewModel = createBankListViewModel()

            viewModel.screenState.test {
                awaitItem() shouldBe emptyList()
                awaitItem() shouldBe listOf("Bank 1", "Bank 2", "Bank 3")
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    private fun createBankListViewModel() = BankListViewModelImpl(bankRepository)
}

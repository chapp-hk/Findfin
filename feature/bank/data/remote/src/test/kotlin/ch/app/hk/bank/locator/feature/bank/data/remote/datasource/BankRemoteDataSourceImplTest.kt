package ch.app.hk.bank.locator.feature.bank.data.remote.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.api.BankApi
import ch.app.hk.bank.locator.feature.bank.data.remote.response.BankApiError
import ch.app.hk.bank.locator.testing.util.readResourceAsJson
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankRemoteDataSourceImpl unit tests")
class BankRemoteDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankApi = mockk<BankApi>()

    private val bankRemoteDataSource =
        BankRemoteDataSourceImpl(
            ioDispatcher = testDispatcher,
            bankApi = bankApi,
        )

    @Test
    @DisplayName("when BankApi.getBankBranches() return success, getBankBranches() should return bank branch list")
    fun testGetBankBranchesSuccess() =
        runTest(testDispatcher.scheduler) {
            coEvery {
                bankApi.getBankBranches(
                    lang = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns readResourceAsJson("branch/success.json")

            val result =
                bankRemoteDataSource.getBankBranches(
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result.size shouldBe 5
        }

    @Test
    @DisplayName("when BankApi.getBankBranches() return empty list, getBankBranches() should return empty list")
    fun testGetBankBranchesEmpty() =
        runTest(testDispatcher.scheduler) {
            coEvery {
                bankApi.getBankBranches(
                    lang = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns readResourceAsJson("branch/empty.json")

            val result =
                bankRemoteDataSource.getBankBranches(
                    language = "en",
                    pageSize = 5,
                    offset = 200,
                )

            result shouldBe emptyList()
        }

    @Test
    @DisplayName("when BankApi.getBankBranches() return error, getBankBranches() should throw BankApiError")
    fun testGetBankBranchesError() =
        runTest(testDispatcher.scheduler) {
            coEvery {
                bankApi.getBankBranches(
                    lang = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns readResourceAsJson("branch/error.json")

            shouldThrowExactly<BankApiError> {
                bankRemoteDataSource.getBankBranches(
                    language = "en",
                    pageSize = 5,
                    offset = 200,
                )
            }
        }
}

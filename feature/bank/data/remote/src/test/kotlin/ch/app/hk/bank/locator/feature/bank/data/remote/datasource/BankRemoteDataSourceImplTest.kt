package ch.app.hk.bank.locator.feature.bank.data.remote.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.api.BankApi
import ch.app.hk.bank.locator.feature.bank.data.remote.response.BankApiError
import ch.app.hk.bank.locator.feature.bank.data.remote.response.Branch
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
    @DisplayName("When BankApi.getBankBranches() return success, getBankBranches() should return bank branch list")
    fun testGetBankBranchesSuccess() =
        runTest(testDispatcher.scheduler) {
            mockBankApiResponse("branch/success.json")

            val result =
                bankRemoteDataSource.getBankBranches(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result.size shouldBe 5
        }

    @Test
    @DisplayName("When BankApi.getBankBranches() return error, getBankBranches() should throw BankApiError")
    fun testGetBankBranchesError() =
        runTest(testDispatcher.scheduler) {
            mockBankApiResponse("branch/error.json")

            shouldThrowExactly<BankApiError> {
                bankRemoteDataSource.getBankBranches(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 10,
                    offset = 40,
                )
            }
        }

    @Test
    @DisplayName(
        "When BankApi.getBankBranches() return empty result records," +
            "getBankBranches() should return empty list",
    )
    fun testGetBankBranchesEmptyResult() =
        runTest(testDispatcher.scheduler) {
            mockBankApiResponse("branch/empty-result-records.json")

            val result =
                bankRemoteDataSource.getBankBranches(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe emptyList()
        }

    @Test
    @DisplayName("When BankApi.getBankBranches() return empty json, getBankBranches() should throw BankApiError")
    fun testGetBankBranchesEmptyJson() =
        runTest(testDispatcher.scheduler) {
            mockBankApiResponse("branch/all-empty.json")

            shouldThrowExactly<BankApiError> {
                bankRemoteDataSource.getBankBranches(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )
            }
        }

    @Test
    @DisplayName(
        "When BankApi.getBankBranches() return success but some fields missing," +
            "getBankBranches() should return list items with default values",
    )
    fun testGetBankBranchesWithMissingFields() =
        runTest(testDispatcher.scheduler) {
            mockBankApiResponse("branch/success-missing-field.json")

            val result =
                bankRemoteDataSource.getBankBranches(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe
                listOf(
                    Branch(
                        district = "",
                        bankName = "The Bank of East Asia Limited",
                        typeName = "Yuen Long i-Teller",
                        address = "G/F, 77 Castle Peak Road, Yuen Long",
                        serviceHours = "Monday - Saturday (9:00 am - 7:00 pm)",
                        latitude = 22.444588,
                        longitude = 114.029541,
                    ),
                    Branch(
                        district = "YuenLong",
                        bankName = "The Bank of East Asia Limited",
                        typeName = "Yuen Long SupremeGold Centre",
                        address = "1/F, 77 Castle Peak Road, Yuen Long",
                        serviceHours = "Monday - Friday (9:00 am - 5:00 pm), Saturday (9:00 am - 1:00 pm)",
                        latitude = 0.0,
                        longitude = 0.0,
                    ),
                )
        }

    @Test
    @DisplayName("When BankApi.getBankBranches() return atm success, getBankBranches() should return list")
    fun testGetBankAtmsSuccess() =
        runTest(testDispatcher.scheduler) {
            mockBankApiResponse("atm/success.json")

            val result =
                bankRemoteDataSource.getBankBranches(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result.size shouldBe 5
        }

    private fun mockBankApiResponse(filePath: String) {
        coEvery {
            bankApi.getBankBranches(
                type = any(),
                lang = any(),
                pageSize = any(),
                offset = any(),
            )
        } returns readResourceAsJson(filePath)
    }
}

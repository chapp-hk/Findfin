package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.response.Bank
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorApiError
import ch.app.hk.bank.locator.testing.util.readResourceAsJson
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorRemoteDataSourceImplTest unit tests")
class LocatorRemoteDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locatorApi = mockk<LocatorApi>()

    private val bankRemoteDataSource =
        LocatorRemoteDataSourceImpl(
            ioDispatcher = testDispatcher,
            locatorApi = locatorApi,
        )

    @Test
    @DisplayName(
        "When LocatorApi.getBanks() return success," +
            "getBanks() should return bank branch list",
    )
    fun testGetBanksSuccess() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success.json")

            val result =
                bankRemoteDataSource.getBanks(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result.size shouldBe 5
        }

    @Test
    @DisplayName(
        "When LocatorApi.getBanks() return error," +
            "getBanks() should throw LocatorApiError",
    )
    fun testGetBanksError() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/error.json")

            shouldThrowExactly<LocatorApiError> {
                bankRemoteDataSource.getBanks(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 10,
                    offset = 40,
                )
            }
        }

    @Test
    @DisplayName(
        "When LocatorApi.getBanks() return empty result records," +
            "getBanks() should return empty list",
    )
    fun testGetBanksEmptyResult() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/empty-result-records.json")

            val result =
                bankRemoteDataSource.getBanks(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe emptyList()
        }

    @Test
    @DisplayName(
        "When LocatorApi.getBanks() return empty json," +
            "getBanks() should throw LocatorApiError",
    )
    fun testGetBanksEmptyJson() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/all-empty.json")

            shouldThrowExactly<LocatorApiError> {
                bankRemoteDataSource.getBanks(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )
            }
        }

    @Test
    @DisplayName(
        "When LocatorApi.getBanks() return success but some fields missing," +
            "getBanks() should return list items with default values",
    )
    fun testGetBanksWithMissingFields() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success-missing-field.json")

            val result =
                bankRemoteDataSource.getBanks(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe
                listOf(
                    Bank(
                        district = "",
                        bankName = "The Bank of East Asia Limited",
                        typeName = "Yuen Long i-Teller",
                        address = "G/F, 77 Castle Peak Road, Yuen Long",
                        serviceHours = "Monday - Saturday (9:00 am - 7:00 pm)",
                        latitude = 22.444588,
                        longitude = 114.029541,
                    ),
                    Bank(
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
    @DisplayName(
        "When LocatorApi.getBanks() return atm success," +
            "getBanks() should return list",
    )
    fun testGetBankAtmsSuccess() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("atm/success.json")

            val result =
                bankRemoteDataSource.getBanks(
                    type = "banks-branch-locator",
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result.size shouldBe 5
        }

    private fun mockApiResponse(filePath: String) {
        coEvery {
            locatorApi.getBanks(
                type = any(),
                lang = any(),
                pageSize = any(),
                offset = any(),
            )
        } returns readResourceAsJson(filePath)
    }
}

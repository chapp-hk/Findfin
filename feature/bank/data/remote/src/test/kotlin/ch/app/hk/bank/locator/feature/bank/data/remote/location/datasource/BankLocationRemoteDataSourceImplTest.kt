package ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.BankLocationApi
import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocationPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.BankLocationResponse
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocationResult
import ch.app.hk.bank.locator.testing.util.readResourceAsJson
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationRemoteDataSourceImpl unit tests")
class BankLocationRemoteDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankLocationApi = mockk<BankLocationApi>()

    private val locatorRemoteDataSource =
        BankLocationRemoteDataSourceImpl(
            ioDispatcher = testDispatcher,
            bankLocationApi = bankLocationApi,
        )

    @Test
    @DisplayName(
        "When BankLocationApi.getLocations() return success, " +
            "getLocations() should return bank branch list",
    )
    fun testGetLocationsSuccess() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success.json")

            val result =
                locatorRemoteDataSource.getLocations(
                    path = LocationPath.BRANCH,
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result
                .shouldBeInstanceOf<LocationResult.Success>()
                .data.size shouldBe 5
        }

    @Test
    @DisplayName(
        "When BankLocationApi.getLocations() return error, " +
            "getLocations() should return LocationResult.Error",
    )
    fun testGetLocationsError() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/error.json")

            val result =
                locatorRemoteDataSource.getLocations(
                    path = LocationPath.BRANCH,
                    language = "en",
                    pageSize = 10,
                    offset = 40,
                )

            result shouldBe LocationResult.Error
        }

    @Test
    @DisplayName(
        "When BankLocationApi.getLocations() return empty result records, " +
            "getLocations() should return empty list",
    )
    fun testGetLocationsEmptyResult() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/empty-result-records.json")

            val result =
                locatorRemoteDataSource.getLocations(
                    path = LocationPath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe LocationResult.Success(emptyList())
        }

    @Test
    @DisplayName(
        "When BankLocationApi.getLocations() return empty json, " +
            "getLocations() should return LocationResult.Error",
    )
    fun testGetLocationsEmptyJson() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/all-empty.json")

            val result =
                locatorRemoteDataSource.getLocations(
                    path = LocationPath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe LocationResult.Error
        }

    @Test
    @DisplayName(
        "When BankLocationApi.getLocations() return success but some fields missing, " +
            "getLocations() should return list items with default values",
    )
    fun testGetLocationsWithMissingFields() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success-missing-field.json")

            val result =
                locatorRemoteDataSource.getLocations(
                    path = LocationPath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result
                .shouldBeInstanceOf<LocationResult.Success>()
                .data shouldBe
                listOf(
                    BankLocationResponse(
                        district = "",
                        bankName = "The Bank of East Asia Limited",
                        typeName = "Yuen Long i-Teller",
                        address = "G/F, 77 Castle Peak Road, Yuen Long",
                        serviceHours = "Monday - Saturday (9:00 am - 7:00 pm)",
                        latitude = 22.444588,
                        longitude = 114.029541,
                    ),
                    BankLocationResponse(
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
        "When BankLocationApi.getLocations() return atm success, " +
            "getLocations() should return list",
    )
    fun testGetLocationsAtmsSuccess() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("atm/success.json")

            val result =
                locatorRemoteDataSource.getLocations(
                    path = LocationPath.ATM,
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result
                .shouldBeInstanceOf<LocationResult.Success>()
                .data.size shouldBe 5
        }

    @Test
    @DisplayName(
        "When invoke getLocations() with BRANCH type, " +
            "should pass \"banks-branch-locator\" to BankLocationApi.getLocations()",
    )
    fun testGetLocationsWithBranchType() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success.json")

            locatorRemoteDataSource.getLocations(
                path = LocationPath.BRANCH,
                language = "en",
                pageSize = 5,
                offset = 100,
            )

            coVerify {
                bankLocationApi.getLocations(
                    path = LocationPath.BRANCH.value,
                    lang = "en",
                    pageSize = 5,
                    offset = 100,
                )
            }
        }

    @Test
    @DisplayName(
        "When invoke getLocations() with ATM type, " +
            "should pass \"banks-atm-locator\" to BankLocationApi.getLocations()",
    )
    fun testGetLocationsWithAtmType() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("atm/success.json")

            locatorRemoteDataSource.getLocations(
                path = LocationPath.ATM,
                language = "en",
                pageSize = 5,
                offset = 100,
            )

            coVerify {
                bankLocationApi.getLocations(
                    path = LocationPath.ATM.value,
                    lang = "en",
                    pageSize = 5,
                    offset = 100,
                )
            }
        }

    private fun mockApiResponse(filePath: String) {
        coEvery {
            bankLocationApi.getLocations(
                path = any(),
                lang = any(),
                pageSize = any(),
                offset = any(),
            )
        } returns readResourceAsJson(filePath)
    }
}

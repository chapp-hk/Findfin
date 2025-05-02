package org.chapp.findfin.feature.bank.data.remote.network.datasource

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.remote.network.api.BankApi
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemoteResult
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.chapp.findfin.testing.util.readResourceAsJson
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationRemoteDataSourceImpl unit tests")
class BankRemoteDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankApi = mockk<BankApi>()

    private val locatorRemoteDataSource =
        BankRemoteDataSourceImpl(
            ioDispatcher = testDispatcher,
            bankApi = bankApi,
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
                    path = TypePath.BRANCH,
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result
                .shouldBeInstanceOf<BankRemoteResult.Success>()
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
                    path = TypePath.BRANCH,
                    language = "en",
                    pageSize = 10,
                    offset = 40,
                )

            result shouldBe BankRemoteResult.Error
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
                    path = TypePath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe BankRemoteResult.Success(emptyList())
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
                    path = TypePath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe BankRemoteResult.Error
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
                    path = TypePath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result
                .shouldBeInstanceOf<BankRemoteResult.Success>()
                .data shouldBe
                listOf(
                    BankRemote(
                        district = "",
                        bankName = "The Bank of East Asia Limited",
                        typeName = "Yuen Long i-Teller",
                        address = "G/F, 77 Castle Peak Road, Yuen Long",
                        serviceHours = "Monday - Saturday (9:00 am - 7:00 pm)",
                        latitude = 22.444588,
                        longitude = 114.029541,
                    ),
                    BankRemote(
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
                    path = TypePath.ATM,
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result
                .shouldBeInstanceOf<BankRemoteResult.Success>()
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
                path = TypePath.BRANCH,
                language = "en",
                pageSize = 5,
                offset = 100,
            )

            coVerify {
                bankApi.getLocations(
                    path = TypePath.BRANCH.value,
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
                path = TypePath.ATM,
                language = "en",
                pageSize = 5,
                offset = 100,
            )

            coVerify {
                bankApi.getLocations(
                    path = TypePath.ATM.value,
                    lang = "en",
                    pageSize = 5,
                    offset = 100,
                )
            }
        }

    private fun mockApiResponse(filePath: String) {
        coEvery {
            bankApi.getLocations(
                path = any(),
                lang = any(),
                pageSize = any(),
                offset = any(),
            )
        } returns readResourceAsJson(filePath)
    }
}

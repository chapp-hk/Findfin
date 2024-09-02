package ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource

import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.BankLocationApi
import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.BankLocationResponse
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocatorResult
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

@DisplayName("LocatorRemoteDataSourceImplTest unit tests")
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
        "When LocatorApi.getLocators() return success, " +
            "getLocators() should return bank branch list",
    )
    fun testGetLocatorsSuccess() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success.json")

            val result =
                locatorRemoteDataSource.getLocators(
                    path = LocatorPath.BRANCH,
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result
                .shouldBeInstanceOf<LocatorResult.Success>()
                .data.size shouldBe 5
        }

    @Test
    @DisplayName(
        "When LocatorApi.getLocators() return error, " +
            "getLocators() should return LocatorResult.Error",
    )
    fun testGetLocatorsError() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/error.json")

            val result =
                locatorRemoteDataSource.getLocators(
                    path = LocatorPath.BRANCH,
                    language = "en",
                    pageSize = 10,
                    offset = 40,
                )

            result shouldBe LocatorResult.Error
        }

    @Test
    @DisplayName(
        "When LocatorApi.getLocators() return empty result records, " +
            "getLocators() should return empty list",
    )
    fun testGetLocatorsEmptyResult() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/empty-result-records.json")

            val result =
                locatorRemoteDataSource.getLocators(
                    path = LocatorPath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe LocatorResult.Success(emptyList())
        }

    @Test
    @DisplayName(
        "When LocatorApi.getLocators() return empty json, " +
            "getLocators() should return LocatorResult.Error",
    )
    fun testGetLocatorsEmptyJson() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/all-empty.json")

            val result =
                locatorRemoteDataSource.getLocators(
                    path = LocatorPath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result shouldBe LocatorResult.Error
        }

    @Test
    @DisplayName(
        "When LocatorApi.getLocators() return success but some fields missing, " +
            "getLocators() should return list items with default values",
    )
    fun testGetLocatorsWithMissingFields() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success-missing-field.json")

            val result =
                locatorRemoteDataSource.getLocators(
                    path = LocatorPath.BRANCH,
                    language = "en",
                    pageSize = 20,
                    offset = 60,
                )

            result
                .shouldBeInstanceOf<LocatorResult.Success>()
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
        "When LocatorApi.getLocators() return atm success, " +
            "getLocators() should return list",
    )
    fun testGetLocatorsAtmsSuccess() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("atm/success.json")

            val result =
                locatorRemoteDataSource.getLocators(
                    path = LocatorPath.ATM,
                    language = "en",
                    pageSize = 5,
                    offset = 100,
                )

            result
                .shouldBeInstanceOf<LocatorResult.Success>()
                .data.size shouldBe 5
        }

    @Test
    @DisplayName(
        "When invoke getLocators() with BRANCH type, " +
            "should pass \"banks-branch-locator\" to LocatorApi.getLocators()",
    )
    fun testGetLocatorsWithBranchType() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("branch/success.json")

            locatorRemoteDataSource.getLocators(
                path = LocatorPath.BRANCH,
                language = "en",
                pageSize = 5,
                offset = 100,
            )

            coVerify {
                bankLocationApi.getLocators(
                    path = LocatorPath.BRANCH.value,
                    lang = "en",
                    pageSize = 5,
                    offset = 100,
                )
            }
        }

    @Test
    @DisplayName(
        "When invoke getLocators() with ATM type, " +
            "should pass \"banks-atm-locator\" to LocatorApi.getLocators()",
    )
    fun testGetLocatorsWithAtmType() =
        runTest(testDispatcher.scheduler) {
            mockApiResponse("atm/success.json")

            locatorRemoteDataSource.getLocators(
                path = LocatorPath.ATM,
                language = "en",
                pageSize = 5,
                offset = 100,
            )

            coVerify {
                bankLocationApi.getLocators(
                    path = LocatorPath.ATM.value,
                    lang = "en",
                    pageSize = 5,
                    offset = 100,
                )
            }
        }

    private fun mockApiResponse(filePath: String) {
        coEvery {
            bankLocationApi.getLocators(
                path = any(),
                lang = any(),
                pageSize = any(),
                offset = any(),
            )
        } returns readResourceAsJson(filePath)
    }
}

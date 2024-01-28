package ch.app.hk.bank.locator.feature.locator.data.remote.datasource

import ch.app.hk.bank.locator.core.network.ApiResult
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorApi
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorApiError
import ch.app.hk.bank.locator.feature.locator.data.remote.response.LocatorResponse
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
class LocatorRemoteDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locatorApi = mockk<LocatorApi>()

    private val locatorRemoteDataSource =
        LocatorRemoteDataSourceImpl(
            ioDispatcher = testDispatcher,
            locatorApi = locatorApi,
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

            result.shouldBeInstanceOf<ApiResult.Success<List<LocatorResponse>>>()
                .data.size shouldBe 5
        }

    @Test
    @DisplayName(
        "When LocatorApi.getLocators() return error, " +
            "getLocators() should return ApiResult.Error",
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

            result
                .shouldBeInstanceOf<ApiResult.Error>()
                .cause shouldBe
                LocatorApiError(
                    errorCode = "1002",
                    errorMessage = "Invalid input value: offset must be non-negative integer",
                )
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

            result.shouldBeInstanceOf<ApiResult.Success<List<LocatorResponse>>>()
                .data shouldBe emptyList()
        }

    @Test
    @DisplayName(
        "When LocatorApi.getLocators() return empty json, " +
            "getLocators() should return ApiResult.Error",
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

            result.shouldBeInstanceOf<ApiResult.Error>()
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

            result.shouldBeInstanceOf<ApiResult.Success<List<LocatorResponse>>>()
                .data shouldBe
                listOf(
                    LocatorResponse(
                        district = "",
                        bankName = "The Bank of East Asia Limited",
                        typeName = "Yuen Long i-Teller",
                        address = "G/F, 77 Castle Peak Road, Yuen Long",
                        serviceHours = "Monday - Saturday (9:00 am - 7:00 pm)",
                        latitude = 22.444588,
                        longitude = 114.029541,
                    ),
                    LocatorResponse(
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

            result.shouldBeInstanceOf<ApiResult.Success<List<LocatorResponse>>>()
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
                locatorApi.getLocators(
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
                locatorApi.getLocators(
                    path = LocatorPath.ATM.value,
                    lang = "en",
                    pageSize = 5,
                    offset = 100,
                )
            }
        }

    private fun mockApiResponse(filePath: String) {
        coEvery {
            locatorApi.getLocators(
                path = any(),
                lang = any(),
                pageSize = any(),
                offset = any(),
            )
        } returns readResourceAsJson(filePath)
    }
}

package org.chapp.findfin.feature.bank.data.repo.repository

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.locale.api.LocaleProviderManager
import org.chapp.findfin.feature.bank.data.repo.datasource.local.datasource.BankLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankQueryParameters
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.datasource.BankRemoteDataSource
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemoteResult
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.TypePath
import org.chapp.findfin.feature.bank.data.repo.mapper.toBankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankRepositoryImpl unit tests")
class BankRepositoryImplTest {
    private val localeProviderManager = mockk<LocaleProviderManager>()
    private val bankLocalDataSource = mockk<BankLocalDataSource>()
    private val bankRemoteDataSource = mockk<BankRemoteDataSource>()

    private val bankRepositoryImpl =
        BankRepositoryImpl(
            localeProviderManager = localeProviderManager,
            bankLocalDataSource = bankLocalDataSource,
            bankRemoteDataSource = bankRemoteDataSource,
        )

    @BeforeEach
    fun setUp() {
        every { localeProviderManager.getCurrentLocaleTag() } returns "en"
        coEvery { bankLocalDataSource.insertAll(any()) } just Runs
    }

    @Test
    @DisplayName(
        "bankLocationRemoteDataSource.getLocations() should invoke with correct offset value: " +
            "page * pageSize",
    )
    fun testOffsetValue() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                bankRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns BankRemoteResult.Error

            bankRepositoryImpl.fetchBanks(
                type = BankType.ATM,
                localeTag = "en",
                page = 2,
                pageSize = 1000,
            )

            coVerify {
                bankRemoteDataSource.getLocations(
                    path = TypePath.ATM,
                    language = "en",
                    pageSize = 1000,
                    offset = 2000,
                )
            }
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns LocatorResult.Error, " +
            "fetchLocations() should return LocationResult.Error",
    )
    fun testFetchBanksError() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                bankRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns BankRemoteResult.Error

            val result =
                bankRepositoryImpl.fetchBanks(
                    type = BankType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = 1000,
                )

            result shouldBe BankFetchResult.Error
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns list with size equals pageSize, " +
            "then fetchLocations() should return LocationResult.HasNext",
    )
    fun testFetchBanksResultListEqualsPageSize() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                BankRemoteResult.Success(
                    (1..pageSize).map { mockk(relaxed = true) },
                )

            val result =
                bankRepositoryImpl.fetchBanks(
                    type = BankType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankFetchResult.HasNext
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns list with size larger than pageSize, " +
            "then fetchLocations() should return LocationResult.HasNext",
    )
    fun testFetchBanksResultListLargerThanPageSize() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                BankRemoteResult.Success(
                    (1..pageSize + 1).map { mockk(relaxed = true) },
                )

            val result =
                bankRepositoryImpl.fetchBanks(
                    type = BankType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankFetchResult.HasNext
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns list with size smaller than pageSize, " +
            "then fetchLocations() should return LocationResult.End",
    )
    fun testFetchBanksEnd() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                BankRemoteResult.Success(
                    (1..pageSize - 10).map { mockk(relaxed = true) },
                )

            val result =
                bankRepositoryImpl.fetchBanks(
                    type = BankType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankFetchResult.End
        }

    @Test
    @DisplayName("When getAllBanks() is successful, should return the list of banks")
    fun testGetAllBanksSuccess() =
        runTest(StandardTestDispatcher()) {
            val expectedBanks = listOf("Bank A", "Bank B", "Bank C")

            coEvery { bankLocalDataSource.getAllBanks(language = any()) } returns expectedBanks

            val result = bankRepositoryImpl.getAllBanks()

            coVerify { bankLocalDataSource.getAllBanks(language = any()) }

            result shouldBe expectedBanks
        }

    @Test
    fun `getBanksByParameters should return empty list when data source returns empty`() =
        runTest {
            val expectedParams =
                BankQueryParameters(
                    language = "en",
                    bankName = null,
                    type = null,
                    minLat = null,
                    maxLat = null,
                    minLon = null,
                    maxLon = null,
                )

            coEvery {
                bankLocalDataSource.getBanksWithParameters(params = any())
            } returns emptyList()

            val result =
                bankRepositoryImpl.getBanksByParameters(name = null, type = null, bound = null)

            result shouldBe emptyList()
            coVerify { bankLocalDataSource.getBanksWithParameters(params = expectedParams) }
        }

    @Test
    fun `getBanksByParameters should return list when data source returns non-empty list`() =
        runTest {
            val mockBankLocal = mockk<BankLocal>(relaxed = true)
            val expectedParams =
                BankQueryParameters(
                    language = "en",
                    bankName = "Bank A",
                    type = BankType.ATM.name,
                    minLat = 1.0,
                    maxLat = 2.0,
                    minLon = 3.0,
                    maxLon = 4.0,
                )

            coEvery {
                bankLocalDataSource.getBanksWithParameters(params = any())
            } returns listOf(mockBankLocal)

            val result =
                bankRepositoryImpl.getBanksByParameters(
                    "Bank A",
                    BankType.ATM,
                    BankLocationBound(minLat = 1.0, maxLat = 2.0, minLong = 3.0, maxLong = 4.0),
                )

            result shouldBe listOf(mockBankLocal.toBankModel())
            coVerify { bankLocalDataSource.getBanksWithParameters(params = expectedParams) }
        }

    @Test
    fun `getBanksByParameters should handle null name and type but non-null bound`() =
        runTest {
            val mockBankLocal = mockk<BankLocal>(relaxed = true)
            val expectedParams =
                BankQueryParameters(
                    language = "en",
                    bankName = null,
                    type = null,
                    minLat = 1.0,
                    maxLat = 2.0,
                    minLon = 3.0,
                    maxLon = 4.0,
                )

            coEvery {
                bankLocalDataSource.getBanksWithParameters(params = any())
            } returns listOf(mockBankLocal)

            val result =
                bankRepositoryImpl.getBanksByParameters(
                    name = null,
                    type = null,
                    bound = BankLocationBound(1.0, 2.0, 3.0, 4.0),
                )

            result shouldBe listOf(mockBankLocal.toBankModel())
            coVerify { bankLocalDataSource.getBanksWithParameters(params = expectedParams) }
        }

    @Test
    fun `getBanksByParameters should handle null bound but non-null name and type`() =
        runTest {
            val mockBankLocal = mockk<BankLocal>(relaxed = true)
            val expectedParams =
                BankQueryParameters(
                    language = "en",
                    bankName = "Bank A",
                    type = BankType.BRANCH.name,
                    minLat = null,
                    maxLat = null,
                    minLon = null,
                    maxLon = null,
                )

            coEvery {
                bankLocalDataSource.getBanksWithParameters(params = any())
            } returns listOf(mockBankLocal)

            val result =
                bankRepositoryImpl.getBanksByParameters(
                    name = "Bank A",
                    type = BankType.BRANCH,
                    bound = null,
                )

            result shouldBe listOf(mockBankLocal.toBankModel())
            coVerify { bankLocalDataSource.getBanksWithParameters(params = expectedParams) }
        }
}

package org.chapp.findfin.feature.bank.data.repo.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.remote.network.api.TypePath
import org.chapp.findfin.feature.bank.data.remote.network.datasource.BankRemoteDataSource
import org.chapp.findfin.feature.bank.data.remote.network.model.BankResult
import org.chapp.findfin.feature.bank.data.repo.local.datasource.BankLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.local.model.BankLocal
import org.chapp.findfin.feature.bank.data.repo.mapper.BankFetchResult
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankRepositoryImpl unit tests")
class BankRepositoryImplTest {
    private val bankLocalDataSource = mockk<BankLocalDataSource>()
    private val bankRemoteDataSource = mockk<BankRemoteDataSource>()

    private val locatorRepositoryImpl =
        BankRepositoryImpl(
            bankLocalDataSource = bankLocalDataSource,
            bankRemoteDataSource = bankRemoteDataSource,
        )

    @BeforeEach
    fun setUp() {
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
            } returns BankResult.Error

            locatorRepositoryImpl.fetchBanks(
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
            } returns BankResult.Error

            val result =
                locatorRepositoryImpl.fetchBanks(
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
                BankResult.Success(
                    (1..pageSize).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchBanks(
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
                BankResult.Success(
                    (1..pageSize + 1).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchBanks(
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
                BankResult.Success(
                    (1..pageSize - 10).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchBanks(
                    type = BankType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankFetchResult.End
        }

    @Test
    fun `getLocationsWithinBound should return correct LocationModel list`() {
        runTest(StandardTestDispatcher()) {
            val mockBound = BankLocationBound(1.0, 1.0, 1.0, 1.0)
            coEvery {
                bankLocalDataSource.getBanksWithinBound(
                    language = any(),
                    minLat = any(),
                    maxLat = any(),
                    minLon = any(),
                    maxLon = any(),
                )
            } returns listOf(mockk(relaxed = true))

            val result = locatorRepositoryImpl.getBanksWithinBound("en", mockBound)

            result.shouldBeInstanceOf<List<BankModel>>()
        }
    }

    @Test
    @DisplayName("When getAllBanks() is successful, should return the list of banks")
    fun testGetAllBanksSuccess() =
        runTest(StandardTestDispatcher()) {
            val expectedBanks = listOf("Bank A", "Bank B", "Bank C")

            coEvery { bankLocalDataSource.getAllBanks() } returns expectedBanks

            val result = locatorRepositoryImpl.getAllBanks()

            coVerify { bankLocalDataSource.getAllBanks() }

            result shouldBe expectedBanks
        }

    @Test
    @DisplayName("When getAll() is successful, should return the list of BankLocationModel")
    fun testGetAllSuccess() =
        runTest(StandardTestDispatcher()) {
            val mockLocalData =
                listOf(
                    BankLocal(
                        language = "en",
                        type = "BRANCH",
                        district = "Kowloon",
                        bankName = "Bank A",
                        typeName = "Branch",
                        address = "Some address",
                        serviceHours = "9:00 - 17:00",
                        latitude = 22.3193,
                        longitude = 114.1694,
                    ),
                )

            coEvery { bankLocalDataSource.getAll() } returns mockLocalData

            val result = locatorRepositoryImpl.getAll()

            coVerify { bankLocalDataSource.getAll() }

            result shouldBe
                listOf(
                    BankModel(
                        type = "BRANCH",
                        district = "Kowloon",
                        bankName = "Bank A",
                        typeName = "Branch",
                        address = "Some address",
                        serviceHours = "9:00 - 17:00",
                        latitude = 22.3193,
                        longitude = 114.1694,
                    ),
                )
        }
}

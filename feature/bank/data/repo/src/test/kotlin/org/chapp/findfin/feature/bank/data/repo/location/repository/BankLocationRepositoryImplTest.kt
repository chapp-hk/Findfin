package org.chapp.findfin.feature.bank.data.repo.location.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.remote.location.api.LocationPath
import org.chapp.findfin.feature.bank.data.remote.location.datasource.BankLocationRemoteDataSource
import org.chapp.findfin.feature.bank.data.remote.location.model.LocationResult
import org.chapp.findfin.feature.bank.data.repo.location.local.datasource.BankLocationLocalDataSource
import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.chapp.findfin.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationModel
import org.chapp.findfin.feature.bank.data.repo.location.model.BankLocationType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocationRepositoryImpl unit tests")
class BankLocationRepositoryImplTest {
    private val bankLocationLocalDataSource = mockk<BankLocationLocalDataSource>()
    private val bankLocationRemoteDataSource = mockk<BankLocationRemoteDataSource>()

    private val locatorRepositoryImpl =
        BankLocationRepositoryImpl(
            bankLocationLocalDataSource = bankLocationLocalDataSource,
            bankLocationRemoteDataSource = bankLocationRemoteDataSource,
        )

    @BeforeEach
    fun setUp() {
        coEvery { bankLocationLocalDataSource.insertAll(any()) } just Runs
    }

    @Test
    @DisplayName(
        "bankLocationRemoteDataSource.getLocations() should invoke with correct offset value: " +
            "page * pageSize",
    )
    fun testOffsetValue() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                bankLocationRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns LocationResult.Error

            locatorRepositoryImpl.fetchLocations(
                type = BankLocationType.ATM,
                localeTag = "en",
                page = 2,
                pageSize = 1000,
            )

            coVerify {
                bankLocationRemoteDataSource.getLocations(
                    path = LocationPath.ATM,
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
    fun testFetchLocationsError() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                bankLocationRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns LocationResult.Error

            val result =
                locatorRepositoryImpl.fetchLocations(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = 1000,
                )

            result shouldBe BankLocationFetchResult.Error
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns list with size equals pageSize, " +
            "then fetchLocations() should return LocationResult.HasNext",
    )
    fun testFetchLocationsResultListEqualsPageSize() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankLocationRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                LocationResult.Success(
                    (1..pageSize).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchLocations(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankLocationFetchResult.HasNext
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns list with size larger than pageSize, " +
            "then fetchLocations() should return LocationResult.HasNext",
    )
    fun testFetchLocationsResultListLargerThanPageSize() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankLocationRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                LocationResult.Success(
                    (1..pageSize + 1).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchLocations(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankLocationFetchResult.HasNext
        }

    @Test
    @DisplayName(
        "When bankLocationRemoteDataSource.getLocations() returns list with size smaller than pageSize, " +
            "then fetchLocations() should return LocationResult.End",
    )
    fun testFetchLocationsEnd() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankLocationRemoteDataSource.getLocations(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                LocationResult.Success(
                    (1..pageSize - 10).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchLocations(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankLocationFetchResult.End
        }

    @Test
    fun `getLocationsWithinBound should return correct LocationModel list`() {
        runTest(StandardTestDispatcher()) {
            val mockBound = BankLocationBound(1.0, 1.0, 1.0, 1.0)
            coEvery {
                bankLocationLocalDataSource.getBanksWithinBound(
                    minLat = any(),
                    maxLat = any(),
                    minLon = any(),
                    maxLon = any(),
                )
            } returns listOf(mockk(relaxed = true))

            val result = locatorRepositoryImpl.getLocationsWithinBound(mockBound)

            result.shouldBeInstanceOf<List<BankLocationModel>>()
        }
    }

    @Test
    @DisplayName("When getAllBanks() is successful, should return the list of banks")
    fun testGetAllBanksSuccess() =
        runTest(StandardTestDispatcher()) {
            val expectedBanks = listOf("Bank A", "Bank B", "Bank C")

            coEvery { bankLocationLocalDataSource.getAllBanks() } returns expectedBanks

            val result = locatorRepositoryImpl.getAllBanks()

            coVerify { bankLocationLocalDataSource.getAllBanks() }

            result shouldBe expectedBanks
        }

    @Test
    @DisplayName("When getAll() is successful, should return the list of BankLocationModel")
    fun testGetAllSuccess() =
        runTest(StandardTestDispatcher()) {
            val mockLocalData =
                listOf(
                    BankLocationLocal(
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

            coEvery { bankLocationLocalDataSource.getAll() } returns mockLocalData

            val result = locatorRepositoryImpl.getAll()

            coVerify { bankLocationLocalDataSource.getAll() }

            result shouldBe
                listOf(
                    BankLocationModel(
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

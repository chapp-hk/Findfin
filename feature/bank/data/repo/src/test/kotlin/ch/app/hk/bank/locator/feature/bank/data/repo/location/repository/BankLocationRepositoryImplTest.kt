package ch.app.hk.bank.locator.feature.bank.data.repo.location.repository

import ch.app.hk.bank.locator.feature.bank.data.local.bank.datasource.BankLocationLocalDataSource
import ch.app.hk.bank.locator.feature.bank.data.remote.location.api.LocatorPath
import ch.app.hk.bank.locator.feature.bank.data.remote.location.datasource.BankLocationRemoteDataSource
import ch.app.hk.bank.locator.feature.bank.data.remote.location.model.LocatorResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.mapper.BankLocationFetchResult
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationBound
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationModel
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationType
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorRepositoryImpl unit tests")
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
        "locatorRemoteDataSource.getLocators() should invoke with correct offset value: " +
            "page * pageSize",
    )
    fun testOffsetValue() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                bankLocationRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns LocatorResult.Error

            locatorRepositoryImpl.fetchLocators(
                type = BankLocationType.ATM,
                localeTag = "en",
                page = 2,
                pageSize = 1000,
            )

            coVerify {
                bankLocationRemoteDataSource.getLocators(
                    path = LocatorPath.ATM,
                    language = "en",
                    pageSize = 1000,
                    offset = 2000,
                )
            }
        }

    @Test
    @DisplayName(
        "When locatorRemoteDataSource.getLocators() returns LocatorResult.Error, " +
            "fetchLocators() should return LocatorResult.Error",
    )
    fun testFetchLocatorsError() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                bankLocationRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns LocatorResult.Error

            val result =
                locatorRepositoryImpl.fetchLocators(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = 1000,
                )

            result shouldBe BankLocationFetchResult.Error
        }

    @Test
    @DisplayName(
        "When locatorRemoteDataSource.getLocators() returns list with size equals pageSize, " +
            "then fetchLocators() should return LocatorResult.HasNext",
    )
    fun testFetchLocatorsResultListEqualsPageSize() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankLocationRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                LocatorResult.Success(
                    (1..pageSize).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchLocators(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankLocationFetchResult.HasNext
        }

    @Test
    @DisplayName(
        "When locatorRemoteDataSource.getLocators() returns list with size larger than pageSize, " +
            "then fetchLocators() should return LocatorResult.HasNext",
    )
    fun testFetchLocatorsResultListLargerThanPageSize() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankLocationRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                LocatorResult.Success(
                    (1..pageSize + 1).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchLocators(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankLocationFetchResult.HasNext
        }

    @Test
    @DisplayName(
        "When locatorRemoteDataSource.getLocators() returns list with size smaller than pageSize, " +
            "then fetchLocators() should return LocatorResult.End",
    )
    fun testFetchLocatorsEnd() =
        runTest(StandardTestDispatcher()) {
            val pageSize = 1000

            coEvery {
                bankLocationRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns
                LocatorResult.Success(
                    (1..pageSize - 10).map { mockk(relaxed = true) },
                )

            val result =
                locatorRepositoryImpl.fetchLocators(
                    type = BankLocationType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe BankLocationFetchResult.End
        }

    @Test
    fun `getLocatorsWithinBound should return correct LocatorModel list`() {
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

            val result = locatorRepositoryImpl.getLocatorsWithinBound(mockBound)

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
}

package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.api.LocatorPath
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.model.LocatorResult
import ch.app.hk.bank.locator.feature.locator.data.repo.mapper.LocatorFetchResult
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocationBound
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorModel
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocatorType
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
class LocatorRepositoryImplTest {
    private val locatorLocalDataSource = mockk<LocatorLocalDataSource>()
    private val locatorRemoteDataSource = mockk<LocatorRemoteDataSource>()

    private val locatorRepositoryImpl =
        LocatorRepositoryImpl(
            locatorLocalDataSource = locatorLocalDataSource,
            locatorRemoteDataSource = locatorRemoteDataSource,
        )

    @BeforeEach
    fun setUp() {
        coEvery { locatorLocalDataSource.insertAll(any()) } just Runs
    }

    @Test
    @DisplayName(
        "locatorRemoteDataSource.getLocators() should invoke with correct offset value: " +
            "page * pageSize",
    )
    fun testOffsetValue() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                locatorRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns LocatorResult.Error

            locatorRepositoryImpl.fetchLocators(
                type = LocatorType.ATM,
                localeTag = "en",
                page = 2,
                pageSize = 1000,
            )

            coVerify {
                locatorRemoteDataSource.getLocators(
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
                locatorRemoteDataSource.getLocators(
                    path = any(),
                    language = any(),
                    pageSize = any(),
                    offset = any(),
                )
            } returns LocatorResult.Error

            val result =
                locatorRepositoryImpl.fetchLocators(
                    type = LocatorType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = 1000,
                )

            result shouldBe LocatorFetchResult.Error
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
                locatorRemoteDataSource.getLocators(
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
                    type = LocatorType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe LocatorFetchResult.HasNext
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
                locatorRemoteDataSource.getLocators(
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
                    type = LocatorType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe LocatorFetchResult.HasNext
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
                locatorRemoteDataSource.getLocators(
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
                    type = LocatorType.ATM,
                    localeTag = "en",
                    page = 2,
                    pageSize = pageSize,
                )

            result shouldBe LocatorFetchResult.End
        }

    @Test
    fun `getLocatorsWithinBound should return correct LocatorModel list`() {
        runTest(StandardTestDispatcher()) {
            val mockBound = LocationBound(1.0, 1.0, 1.0, 1.0)
            coEvery {
                locatorLocalDataSource.getLocatorsWithinBound(
                    minLat = any(),
                    maxLat = any(),
                    minLon = any(),
                    maxLon = any(),
                )
            } returns listOf(mockk(relaxed = true))

            val result = locatorRepositoryImpl.getLocatorsWithinBound(mockBound)

            result.shouldBeInstanceOf<List<LocatorModel>>()
        }
    }
}

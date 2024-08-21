package ch.app.hk.bank.locator.feature.locator.data.local.database.datasource

import ch.app.hk.bank.locator.feature.locator.data.local.database.model.LocatorEntity
import ch.app.hk.bank.locator.feature.locator.data.local.database.room.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.model.LocatorLocal
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorLocalDataSourceImpl unit tests")
class LocatorLocalDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locatorDao = mockk<LocatorDao>()

    private val locatorLocalDataSourceImpl =
        LocatorLocalDataSourceImpl(
            ioDispatcher = testDispatcher,
            locatorDao = locatorDao,
        )

    @Test
    @DisplayName(
        "When invoke insertAll() with list of BankLocal, " +
            "should convert to list of LocatorEntity and invoke LocatorDao.insertAll()",
    )
    fun testInsertAll() =
        runTest(testDispatcher) {
            val locatorEntityListSlot = slot<List<LocatorEntity>>()

            coEvery {
                locatorDao.insertAll(capture(locatorEntityListSlot))
            } just Runs

            locatorLocalDataSourceImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                locatorDao.insertAll(locatorEntityListSlot.captured)
            }
        }

    @Test
    @DisplayName(
        "When invoke insertAll() and LocatorDao.insertAll() throws an exception, " +
            "should catch the exception and not propagate it",
    )
    fun testInsertAllErrorHandling() =
        runTest(testDispatcher) {
            coEvery {
                locatorDao.insertAll(any())
            } throws Exception("Test exception")

            locatorLocalDataSourceImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                locatorDao.insertAll(any())
            }
        }

    @Test
    @DisplayName(
        "When invoke getLocatorsWithinBound() successfully, " +
            "should return the list of LocatorLocal",
    )
    fun testGetLocatorsWithinBoundSuccess() =
        runTest(testDispatcher) {
            coEvery {
                locatorDao.getLocatorsWithinBound(any(), any(), any(), any())
            } returns
                listOf(
                    LocatorEntity(
                        type = "bank",
                        district = "mock district",
                        bankName = "mock bank name",
                        typeName = "mock type name",
                        address = "mock address",
                        serviceHours = "mock service hours",
                        latitude = 0.0,
                        longitude = 0.0,
                    ),
                )

            val result =
                locatorLocalDataSourceImpl.getLocatorsWithinBound(
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )

            coVerify {
                locatorDao.getLocatorsWithinBound(
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )
            }

            result shouldBe
                listOf(
                    LocatorLocal(
                        type = "bank",
                        district = "mock district",
                        bankName = "mock bank name",
                        typeName = "mock type name",
                        address = "mock address",
                        serviceHours = "mock service hours",
                        latitude = 0.0,
                        longitude = 0.0,
                    ),
                )
        }

    @Test
    @DisplayName(
        "When invoke getLocatorsWithinBound() and LocatorDao.getLocatorsWithinBound() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetLocatorsWithinBoundErrorHandling() =
        runTest(testDispatcher) {
            coEvery {
                locatorDao.getLocatorsWithinBound(any(), any(), any(), any())
            } throws Exception("Test exception")

            val result =
                locatorLocalDataSourceImpl.getLocatorsWithinBound(
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )

            coVerify {
                locatorDao.getLocatorsWithinBound(any(), any(), any(), any())
            }

            result shouldBe emptyList()
        }

    @Test
    @DisplayName("When invoke getAllBanks() successfully, should return the list of banks")
    fun testGetAllBanksSuccess() =
        runTest(testDispatcher) {
            val expectedBanks = listOf("Bank A", "Bank B", "Bank C")

            coEvery { locatorDao.getDistinctBanks() } returns expectedBanks

            val result = locatorLocalDataSourceImpl.getAllBanks()

            coVerify { locatorDao.getDistinctBanks() }

            result shouldBe expectedBanks
        }

    @Test
    @DisplayName(
        "When invoke getAllBanks() and LocatorDao.getDistinctBanks() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetAllBanksErrorHandling() =
        runTest(testDispatcher) {
            coEvery { locatorDao.getDistinctBanks() } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAllBanks()

            coVerify { locatorDao.getDistinctBanks() }

            result shouldBe emptyList()
        }
}

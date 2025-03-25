package org.chapp.findfin.feature.bank.data.local.database.location.datasource

import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.local.database.location.dao.BankLocationDao
import org.chapp.findfin.feature.bank.data.local.database.location.model.BankLocationEntity
import org.chapp.findfin.feature.bank.data.repo.location.local.model.BankLocationLocal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorLocalDataSourceImpl unit tests")
class BankLocationLocalDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankLocationDao = mockk<BankLocationDao>()

    private val locatorLocalDataSourceImpl =
        BankLocationLocalDataSourceImpl(
            ioDispatcher = testDispatcher,
            bankLocationDao = bankLocationDao,
        )

    @Test
    @DisplayName(
        "When invoke insertAll() with list of BankLocal, " +
            "should convert to list of LocatorEntity and invoke LocatorDao.insertAll()",
    )
    fun testInsertAll() =
        runTest(testDispatcher) {
            val bankLocationEntityListSlot = slot<List<BankLocationEntity>>()

            coEvery {
                bankLocationDao.insertAll(capture(bankLocationEntityListSlot))
            } just Runs

            locatorLocalDataSourceImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                bankLocationDao.insertAll(bankLocationEntityListSlot.captured)
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
                bankLocationDao.insertAll(any())
            } throws Exception("Test exception")

            locatorLocalDataSourceImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                bankLocationDao.insertAll(any())
            }
        }

    @Test
    @DisplayName(
        "When invoke getLocatorsWithinBound() successfully, " +
            "should return the list of LocatorLocal",
    )
    fun testGetBanksWithinBoundSuccess() =
        runTest(testDispatcher) {
            coEvery {
                bankLocationDao.getLocatorsWithinBound(
                    language = any(),
                    minLat = any(),
                    maxLat = any(),
                    minLon = any(),
                    maxLon = any(),
                )
            } returns
                listOf(
                    BankLocationEntity(
                        type = "bank",
                        language = "en",
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
                locatorLocalDataSourceImpl.getBanksWithinBound(
                    language = "en",
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )

            coVerify {
                bankLocationDao.getLocatorsWithinBound(
                    language = "en",
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )
            }

            result shouldBe
                listOf(
                    BankLocationLocal(
                        type = "bank",
                        language = "en",
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
    fun testGetBanksWithinBoundErrorHandling() =
        runTest(testDispatcher) {
            coEvery {
                bankLocationDao.getLocatorsWithinBound(
                    language = any(),
                    minLat = any(),
                    maxLat = any(),
                    minLon = any(),
                    maxLon = any(),
                )
            } throws Exception("Test exception")

            val result =
                locatorLocalDataSourceImpl.getBanksWithinBound(
                    language = "en",
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )

            coVerify {
                bankLocationDao.getLocatorsWithinBound(
                    language = "en",
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )
            }

            result shouldBe emptyList()
        }

    @Test
    @DisplayName("When invoke getAllBanks() successfully, should return the list of banks")
    fun testGetAllBanksSuccess() =
        runTest(testDispatcher) {
            val expectedBanks = listOf("Bank A", "Bank B", "Bank C")

            coEvery { bankLocationDao.getDistinctBanks() } returns expectedBanks

            val result = locatorLocalDataSourceImpl.getAllBanks()

            coVerify { bankLocationDao.getDistinctBanks() }

            result shouldBe expectedBanks
        }

    @Test
    @DisplayName(
        "When invoke getAllBanks() and LocatorDao.getDistinctBanks() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetAllBanksErrorHandling() =
        runTest(testDispatcher) {
            coEvery { bankLocationDao.getDistinctBanks() } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAllBanks()

            coVerify { bankLocationDao.getDistinctBanks() }

            result shouldBe emptyList()
        }

    @Test
    @DisplayName("When invoke getAll() successfully, should return the list of LocatorLocal")
    fun testGetAllSuccess() =
        runTest(testDispatcher) {
            coEvery { bankLocationDao.getAll() } returns
                listOf(
                    BankLocationEntity(
                        type = "bank",
                        language = "en",
                        district = "mock district",
                        bankName = "mock bank name",
                        typeName = "mock type name",
                        address = "mock address",
                        serviceHours = "mock service hours",
                        latitude = 0.0,
                        longitude = 0.0,
                    ),
                )

            val result = locatorLocalDataSourceImpl.getAll()

            coVerify { bankLocationDao.getAll() }

            result shouldBe
                listOf(
                    BankLocationLocal(
                        type = "bank",
                        language = "en",
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
        "When invoke getAll() and LocatorDao.getAll() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetAllErrorHandling() =
        runTest(testDispatcher) {
            coEvery { bankLocationDao.getAll() } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAll()

            coVerify { bankLocationDao.getAll() }

            result shouldBe emptyList()
        }
}

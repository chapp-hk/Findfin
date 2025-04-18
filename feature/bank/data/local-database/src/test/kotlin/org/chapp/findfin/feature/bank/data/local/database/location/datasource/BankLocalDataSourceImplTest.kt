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
import org.chapp.findfin.feature.bank.data.local.database.dao.BankDao
import org.chapp.findfin.feature.bank.data.local.database.datasource.BankLocalDataSourceImpl
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankLocal
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BankLocalDataSourceImpl unit tests")
class BankLocalDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val bankDao = mockk<BankDao>()

    private val locatorLocalDataSourceImpl =
        BankLocalDataSourceImpl(
            ioDispatcher = testDispatcher,
            bankDao = bankDao,
        )

    @Test
    @DisplayName(
        "When invoke insertAll() with list of BankLocal, " +
            "should convert to list of LocatorEntity and invoke BankDao.insertAll()",
    )
    fun testInsertAll() =
        runTest(testDispatcher) {
            val bankEntityListSlot = slot<List<BankEntity>>()

            coEvery {
                bankDao.insertAll(capture(bankEntityListSlot))
            } just Runs

            locatorLocalDataSourceImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                bankDao.insertAll(bankEntityListSlot.captured)
            }
        }

    @Test
    @DisplayName(
        "When invoke insertAll() and BankDao.insertAll() throws an exception, " +
            "should catch the exception and not propagate it",
    )
    fun testInsertAllErrorHandling() =
        runTest(testDispatcher) {
            coEvery {
                bankDao.insertAll(any())
            } throws Exception("Test exception")

            locatorLocalDataSourceImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                bankDao.insertAll(any())
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
                bankDao.getBanksWithinBound(
                    language = any(),
                    minLat = any(),
                    maxLat = any(),
                    minLon = any(),
                    maxLon = any(),
                )
            } returns
                listOf(
                    BankEntity(
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
                bankDao.getBanksWithinBound(
                    language = "en",
                    minLat = 0.0,
                    maxLat = 0.0,
                    minLon = 0.0,
                    maxLon = 0.0,
                )
            }

            result shouldBe
                listOf(
                    BankLocal(
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
        "When invoke getLocatorsWithinBound() and BankDao.getBanksWithinBound() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetBanksWithinBoundErrorHandling() =
        runTest(testDispatcher) {
            coEvery {
                bankDao.getBanksWithinBound(
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
                bankDao.getBanksWithinBound(
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

            coEvery { bankDao.getDistinctBanks() } returns expectedBanks

            val result = locatorLocalDataSourceImpl.getAllBanks()

            coVerify { bankDao.getDistinctBanks() }

            result shouldBe expectedBanks
        }

    @Test
    @DisplayName(
        "When invoke getAllBanks() and BankDao.getDistinctBanks() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetAllBanksErrorHandling() =
        runTest(testDispatcher) {
            coEvery { bankDao.getDistinctBanks() } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAllBanks()

            coVerify { bankDao.getDistinctBanks() }

            result shouldBe emptyList()
        }

    @Test
    @DisplayName("When invoke getAll() successfully, should return the list of LocatorLocal")
    fun testGetAllSuccess() =
        runTest(testDispatcher) {
            coEvery { bankDao.getAll() } returns
                listOf(
                    BankEntity(
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

            coVerify { bankDao.getAll() }

            result shouldBe
                listOf(
                    BankLocal(
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
        "When invoke getAll() and BankDao.getAll() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetAllErrorHandling() =
        runTest(testDispatcher) {
            coEvery { bankDao.getAll() } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAll()

            coVerify { bankDao.getAll() }

            result shouldBe emptyList()
        }
}

package org.chapp.findfin.feature.bank.data.local.database.location.datasource

import androidx.sqlite.db.SupportSQLiteQuery
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
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

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
    @DisplayName("When invoke getAllBanks() successfully, should return the list of banks")
    fun testGetAllBanksSuccess() =
        runTest(testDispatcher) {
            val expectedBanks = listOf("Bank A", "Bank B", "Bank C")

            coEvery { bankDao.getDistinctBankNames(language = any()) } returns expectedBanks

            val result = locatorLocalDataSourceImpl.getAllBanks(language = "en")

            coVerify { bankDao.getDistinctBankNames(language = "en") }

            result shouldBe expectedBanks
        }

    @Test
    @DisplayName(
        "When invoke getAllBanks() and BankDao.getDistinctBanks() throws an exception, " +
            "should catch the exception and return an empty list",
    )
    fun testGetAllBanksErrorHandling() =
        runTest(testDispatcher) {
            coEvery { bankDao.getDistinctBankNames(language = any()) } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAllBanks(language = "zh")

            coVerify { bankDao.getDistinctBankNames(language = "zh") }

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

    @ParameterizedTest
    @ArgumentsSource(GetBanksQueryParametersArgumentProvider::class)
    @DisplayName("Test getBanksWithParameters() with various inputs")
    fun testGetBanksWithParameters(
        language: String,
        bankName: String?,
        type: String?,
        minLat: Double?,
        maxLat: Double?,
        minLon: Double?,
        maxLon: Double?,
        expectedSql: String,
        expectedArgCount: Int,
    ) = runTest(testDispatcher) {
        val querySlot = slot<SupportSQLiteQuery>()
        coEvery { bankDao.getBanksWithQuery(capture(querySlot)) } returns emptyList()

        locatorLocalDataSourceImpl.getBanksWithParameters(
            language = language,
            bankName = bankName,
            type = type,
            minLat = minLat,
            maxLat = maxLat,
            minLon = minLon,
            maxLon = maxLon,
        )

        querySlot.captured.sql shouldBe expectedSql
        querySlot.captured.argCount shouldBe expectedArgCount
        coVerify { bankDao.getBanksWithQuery(querySlot.captured) }
    }

    private class GetBanksQueryParametersArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    "en", null, null, null, null, null, null,
                    "SELECT * FROM bank WHERE 1=1 AND language = ?",
                    1,
                ),
                Arguments.of(
                    "en", "Bank A", null, null, null, null, null,
                    "SELECT * FROM bank WHERE 1=1 AND language = ? AND bank_name = ?",
                    2,
                ),
                Arguments.of(
                    "en", null, "type1", null, null, null, null,
                    "SELECT * FROM bank WHERE 1=1 AND language = ? AND type = ?",
                    2,
                ),
                Arguments.of(
                    "en", null, null, 10.0, 20.0, null, null,
                    "SELECT * FROM bank WHERE 1=1 AND language = ? AND latitude BETWEEN ? AND ?",
                    3,
                ),
                Arguments.of(
                    "en", null, null, null, null, 30.0, 40.0,
                    "SELECT * FROM bank WHERE 1=1 AND language = ? AND longitude BETWEEN ? AND ?",
                    3,
                ),
                Arguments.of(
                    "en", "Bank A", "type1", 10.0, 20.0, 30.0, 40.0,
                    "SELECT * FROM bank WHERE 1=1 " +
                        "AND language = ? " +
                        "AND type = ? AND " +
                        "bank_name = ? " +
                        "AND latitude BETWEEN ? AND ? " +
                        "AND longitude BETWEEN ? AND ?",
                    7,
                ),
            )
    }
}

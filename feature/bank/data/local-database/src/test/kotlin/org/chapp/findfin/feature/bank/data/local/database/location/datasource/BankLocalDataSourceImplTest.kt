package org.chapp.findfin.feature.bank.data.local.database.location.datasource

import androidx.sqlite.db.SupportSQLiteQuery
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.local.database.dao.BankDao
import org.chapp.findfin.feature.bank.data.local.database.datasource.BankLocalDataSourceImpl
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.repo.datasource.local.model.BankQueryParameters
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream

@DisplayName("BankLocalDataSourceImpl unit tests")
class BankLocalDataSourceImplTest {
    private val bankDao = mockk<BankDao>()

    private val locatorLocalDataSourceImpl =
        BankLocalDataSourceImpl(
            bankDao = bankDao,
        )

    @Test
    @DisplayName(
        "When invoke insertAll() with list of BankLocal, " +
            "should convert to list of LocatorEntity and invoke BankDao.insertAll()",
    )
    fun testInsertAll() =
        runTest {
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
        runTest {
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
        runTest {
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
        runTest {
            coEvery { bankDao.getDistinctBankNames(language = any()) } throws Exception("Test exception")

            val result = locatorLocalDataSourceImpl.getAllBanks(language = "zh")

            coVerify { bankDao.getDistinctBankNames(language = "zh") }

            result shouldBe emptyList()
        }

    @Test
    fun `Test getBanksWithParameters() with error `() =
        runTest {
            coEvery { bankDao.getBanksWithQuery(any()) } throws Error()

            val result = locatorLocalDataSourceImpl.getBanksWithParameters(BankQueryParameters(language = "en"))

            result shouldBe emptyList()
        }

    @ParameterizedTest
    @ArgumentsSource(GetBanksQueryParametersArgumentProvider::class)
    @DisplayName("Test getBanksWithParameters() with various inputs")
    fun testGetBanksWithParameters(
        params: BankQueryParameters,
        expectedSql: String,
        expectedArgCount: Int,
    ) = runTest {
        val querySlot = slot<SupportSQLiteQuery>()
        coEvery { bankDao.getBanksWithQuery(capture(querySlot)) } returns emptyList()

        locatorLocalDataSourceImpl.getBanksWithParameters(params)

        querySlot.captured.sql shouldBe expectedSql
        querySlot.captured.argCount shouldBe expectedArgCount
        coVerify { bankDao.getBanksWithQuery(querySlot.captured) }
    }

    private class GetBanksQueryParametersArgumentProvider : ArgumentsProvider {
        override fun provideArguments(
            parameterDeclarations: ParameterDeclarations,
            context: ExtensionContext,
        ): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    BankQueryParameters(language = "en"),
                    "SELECT * FROM bank WHERE 1=1 AND language = ?",
                    1,
                ),
                Arguments.of(
                    BankQueryParameters(language = "en", bankName = "Bank A"),
                    "SELECT * FROM bank WHERE 1=1 AND language = ? AND bank_name = ?",
                    2,
                ),
                Arguments.of(
                    BankQueryParameters(language = "en", type = "type1"),
                    "SELECT * FROM bank WHERE 1=1 AND language = ? AND type = ?",
                    2,
                ),
                Arguments.of(
                    BankQueryParameters(language = "en", minLatitude = 10.0),
                    "SELECT * FROM bank WHERE 1=1 AND language = ?",
                    1,
                ),
                Arguments.of(
                    BankQueryParameters(language = "en", minLatitude = 10.0, maxLatitude = 20.0),
                    "SELECT * FROM bank WHERE 1=1 AND language = ?",
                    1,
                ),
                Arguments.of(
                    BankQueryParameters(
                        language = "en",
                        minLatitude = 10.0,
                        maxLatitude = 20.0,
                        minLongitude = 30.0,
                    ),
                    "SELECT * FROM bank WHERE 1=1 AND language = ?",
                    1,
                ),
                Arguments.of(
                    BankQueryParameters(
                        language = "en",
                        bankName = "Bank A",
                        type = "type1",
                        minLatitude = 10.0,
                        maxLatitude = 20.0,
                        minLongitude = 30.0,
                        maxLongitude = 40.0,
                    ),
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

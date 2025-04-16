package org.chapp.findfin.feature.bank.data.local.database.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.local.database.model.BankEntity
import org.chapp.findfin.feature.bank.data.local.room.BankDatabase
import org.junit.Test

class BankDaoTest {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun testInsertAll_withNoDuplicateId() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankEntity(
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    BankEntity(
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                )

            database.bankDao.insertAll(list)

            database.query("SELECT * FROM locator", null).use {
                it.count shouldBe 2
            }
        }
    }

    @Test
    fun testInsertAll_withDuplicateId() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankEntity(
                        id = 1,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    BankEntity(
                        id = 2,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                    BankEntity(
                        id = 1,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of East Asia",
                        typeName = "ATM",
                        address = "10 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                )

            database.bankDao.insertAll(list)

            database.bankDao.getAll() shouldBe
                listOf(
                    BankEntity(
                        id = 1,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "Bank of East Asia",
                        typeName = "ATM",
                        address = "10 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    BankEntity(
                        id = 2,
                        type = "ATM",
                        language = "en",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                )
        }
    }

    @Test
    fun testGetLocatorsWithinBound() {
        runTest(testDispatcher) {
            val database =
                Room
                    .databaseBuilder(
                        context,
                        BankDatabase::class.java,
                        "locator_db.db",
                    ).createFromAsset("locator_db.db")
                    .build()

            database
                .bankDao
                .getLocatorsWithinBound(
                    language = "en",
                    minLat = 22.294630813707222,
                    maxLat = 22.312641530083468,
                    minLon = 114.22859313885354,
                    maxLon = 114.24806027679244,
                ).size shouldBe 40
        }
    }

    @Test
    fun testGetAll() {
        runTest(testDispatcher) {
            val database =
                Room
                    .databaseBuilder(
                        context,
                        BankDatabase::class.java,
                        "locator_db.db",
                    ).createFromAsset("locator_db.db")
                    .build()

            database.bankDao.getAll().size shouldBe 6194
        }
    }
}

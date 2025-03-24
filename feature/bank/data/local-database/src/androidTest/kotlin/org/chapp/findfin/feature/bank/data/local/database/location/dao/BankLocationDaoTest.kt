package org.chapp.findfin.feature.bank.data.local.database.location.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.local.database.location.model.BankLocationEntity
import org.chapp.findfin.feature.bank.data.local.database.room.BankDatabase
import org.junit.Test

class BankLocationDaoTest {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun testInsertAll_withNoDuplicateId() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankLocationEntity(
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
                    BankLocationEntity(
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

            database.bankLocationDao.insertAll(list)

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
                    BankLocationEntity(
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
                    BankLocationEntity(
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
                    BankLocationEntity(
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

            database.bankLocationDao.insertAll(list)

            database.bankLocationDao.getAll() shouldBe
                listOf(
                    BankLocationEntity(
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
                    BankLocationEntity(
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
                .bankLocationDao
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

            database.bankLocationDao.getAll().size shouldBe 6194
        }
    }
}

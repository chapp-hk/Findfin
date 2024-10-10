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
    fun testInsertAll_withNoDuplicate() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankLocationEntity(
                        type = "ATM",
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
    fun testInsertAll_withDuplicate() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val list =
                listOf(
                    BankLocationEntity(
                        type = "ATM",
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
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                    BankLocationEntity(
                        type = "ATM",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                )

            database.bankLocationDao.insertAll(list)

            database.query("SELECT * FROM locator", null).use {
                it.count shouldBe 2
            }
        }
    }

    @Test
    fun testInsertAll_replaceExisting() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, BankDatabase::class.java).build()

            val firstList =
                listOf(
                    BankLocationEntity(
                        type = "ATM",
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
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                )

            database.bankLocationDao.insertAll(firstList)

            val secondList =
                listOf(
                    BankLocationEntity(
                        type = "ATM",
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
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707123,
                        longitude = 114.22859313885455,
                    ),
                )

            database.bankLocationDao.insertAll(secondList)

            database.query("SELECT * FROM locator", null).use {
                it.count shouldBe 3
            }
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
                    minLat = 22.294630813707222,
                    maxLat = 22.312641530083468,
                    minLon = 114.22859313885354,
                    maxLon = 114.24806027679244,
                ).size shouldBe 30
        }
    }

    @Test
    fun testGetAllDistinctBanks() {
        runTest(testDispatcher) {
            val database =
                Room
                    .databaseBuilder(
                        context,
                        BankDatabase::class.java,
                        "locator_db.db",
                    ).createFromAsset("locator_db.db")
                    .build()

            database.bankLocationDao.getDistinctBanks() shouldBe
                listOf(
                    "The Bank of East Asia Limited",
                    "Fubon Bank (Hong Kong) Limited",
                    "CMB Wing Lung Bank Limited",
                    "Hang Seng Bank Limited",
                    "The Hongkong and Shanghai Banking Corporation Limited",
                    "Shanghai Commercial Bank Limited",
                    "Citibank (Hong Kong) Limited",
                    "China Construction Bank (Asia) Corporation Limited",
                    "Bank of Communications Co., Ltd.",
                    "Bank of China (Hong Kong) Limited",
                    "Public Bank (Hong Kong) Limited",
                    "OCBC Bank (Hong Kong) Limited",
                    "Industrial and Commercial Bank of China (Asia) Limited",
                    "Chong Hing Bank Limited",
                    "China CITIC Bank International Limited",
                    "Standard Chartered Bank (Hong Kong) Limited",
                    "Chiyu Banking Corporation Ltd.",
                    "Dah Sing Bank, Limited",
                    "DBS Bank (Hong Kong) Limited",
                    "Nanyang Commercial Bank, Limited",
                )
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

            database.bankLocationDao.getAll().size shouldBe 2139
        }
    }
}

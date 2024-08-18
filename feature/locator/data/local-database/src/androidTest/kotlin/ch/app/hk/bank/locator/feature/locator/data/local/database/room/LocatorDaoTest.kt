package ch.app.hk.bank.locator.feature.locator.data.local.database.room

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import ch.app.hk.bank.locator.feature.locator.data.local.database.model.LocatorEntity
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LocatorDaoTest {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun testInsertAll_withNoDuplicate() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, LocatorDatabase::class.java).build()

            val list =
                listOf(
                    LocatorEntity(
                        type = "ATM",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    LocatorEntity(
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

            database.locatorDao.insertAll(list)

            database.query("SELECT * FROM locator", null).use {
                it.count shouldBe 2
            }
        }
    }

    @Test
    fun testInsertAll_withDuplicate() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, LocatorDatabase::class.java).build()

            val list =
                listOf(
                    LocatorEntity(
                        type = "ATM",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    LocatorEntity(
                        type = "ATM",
                        district = "Central and Western",
                        bankName = "HSBC",
                        typeName = "ATM",
                        address = "1 Queen's Road Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.312641530083468,
                        longitude = 114.24806027679244,
                    ),
                    LocatorEntity(
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

            database.locatorDao.insertAll(list)

            database.query("SELECT * FROM locator", null).use {
                it.count shouldBe 2
            }
        }
    }

    @Test
    fun testInsertAll_replaceExisting() {
        runTest(testDispatcher) {
            val database = Room.inMemoryDatabaseBuilder(context, LocatorDatabase::class.java).build()

            val firstList =
                listOf(
                    LocatorEntity(
                        type = "ATM",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    LocatorEntity(
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

            database.locatorDao.insertAll(firstList)

            val secondList =
                listOf(
                    LocatorEntity(
                        type = "ATM",
                        district = "Central and Western",
                        bankName = "Bank of China",
                        typeName = "ATM",
                        address = "1 Garden Road, Central, Hong Kong",
                        serviceHours = "24 hours",
                        latitude = 22.294630813707222,
                        longitude = 114.22859313885354,
                    ),
                    LocatorEntity(
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

            database.locatorDao.insertAll(secondList)

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
                        LocatorDatabase::class.java,
                        "locator_db.db",
                    ).createFromAsset("locator_db.db")
                    .build()

            database
                .locatorDao
                .getLocatorsWithinBound(
                    minLat = 22.294630813707222,
                    maxLat = 22.312641530083468,
                    minLon = 114.22859313885354,
                    maxLon = 114.24806027679244,
                ).size shouldBe 30
        }
    }
}

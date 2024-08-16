package ch.app.hk.bank.locator.feature.locator.data.local.database.room

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LocatorDaoTest {
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun testGetLocatorsWithinBound() {
        runTest(testDispatcher) {
            val database = createDatabase()
            database
                .locatorDao
                .getLocatorsWithinBound(
                    minLat = 22.294630813707222,
                    maxLat = 22.312641530083468,
                    minLon = 114.22859313885354,
                    maxLon = 114.24806027679244,
                ).size shouldBe 0
        }
    }

    @Test
    fun testGetAllBanks() {
        runTest(testDispatcher) {
            val database = createDatabase()
            database.locatorDao.getAllBanks().size shouldBe 0
        }
    }

    private fun createDatabase(): LocatorDatabase {
        return Room.databaseBuilder(
            context,
            LocatorDatabase::class.java,
            "locator_db.db",
        ).createFromAsset("locator_db.db")
            .build()
    }
}

package ch.app.hk.bank.locator.feature.locator.data.local.database.dao

import ch.app.hk.bank.locator.feature.locator.data.local.database.datasource.LocatorLocalDataSourceImpl
import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.LocatorEntity
import ch.app.hk.bank.locator.feature.locator.data.local.database.room.LocatorDao
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

@DisplayName("LocatorLocalDaoImpl unit tests")
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
            "should convert to list of LocatorEntity and invoke LocatorLocalDaoImpl.insertAll()",
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
}

package ch.app.hk.bank.locator.feature.locator.data.local.datasource

import ch.app.hk.bank.locator.feature.locator.data.local.dao.LocatorDao
import ch.app.hk.bank.locator.feature.locator.data.local.entity.LocatorLocal
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

@DisplayName("LocatorLocalDataSourceImpl unit tests")
class LocatorLocalDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locatorDao = mockk<LocatorDao>()

    private val locatorLocalDataSource =
        LocatorLocalDataSourceImpl(
            ioDispatcher = testDispatcher,
            locatorDao = locatorDao,
        )

    @Test
    @DisplayName("When invoke insertAll(), should invoke LocatorDao.insertAll()")
    fun testInsertAll() =
        runTest(testDispatcher) {
            val locatorLocalListSlot = slot<List<LocatorLocal>>()

            coEvery {
                locatorDao.insertAll(capture(locatorLocalListSlot))
            } just Runs

            locatorLocalDataSource.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                locatorDao.insertAll(locatorLocalListSlot.captured)
            }
        }
}

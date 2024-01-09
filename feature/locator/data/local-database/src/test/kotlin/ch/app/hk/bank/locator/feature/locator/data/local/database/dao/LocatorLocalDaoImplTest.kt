package ch.app.hk.bank.locator.feature.locator.data.local.database.dao

import ch.app.hk.bank.locator.feature.locator.data.local.database.entity.LocatorEntity
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
class LocatorLocalDaoImplTest {
    private val locatorRoomDao = mockk<LocatorRoomDao>()

    private val locatorLocalDaoImpl =
        LocatorLocalDaoImpl(
            locatorRoomDao = locatorRoomDao,
        )

    @Test
    @DisplayName(
        "When invoke insertAll() with list of BankLocal, " +
            "should convert to list of LocatorEntity and invoke LocatorLocalDaoImpl.insertAll()",
    )
    fun testInsertAll() =
        runTest(StandardTestDispatcher()) {
            val locatorEntityListSlot = slot<List<LocatorEntity>>()

            coEvery {
                locatorRoomDao.insertAll(capture(locatorEntityListSlot))
            } just Runs

            locatorLocalDaoImpl.insertAll(listOf(mockk(relaxed = true)))

            coVerify {
                locatorRoomDao.insertAll(locatorEntityListSlot.captured)
            }
        }
}

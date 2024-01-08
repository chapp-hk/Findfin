package ch.app.hk.bank.locator.feature.locator.data.repo.repository

import ch.app.hk.bank.locator.feature.locator.data.local.datasource.LocatorLocalDataSource
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocatorRepositoryImpl unit tests")
class LocatorRepositoryImplTest {
    private val locatorLocalDataSource = mockk<LocatorLocalDataSource>()
    private val locatorRemoteDataSource = mockk<LocatorRemoteDataSource>()

    private val locatorRepositoryImpl =
        LocatorRepositoryImpl(
            locatorLocalDataSource = locatorLocalDataSource,
            locatorRemoteDataSource = locatorRemoteDataSource,
        )

    @BeforeEach
    fun setUp() {
        coEvery { locatorLocalDataSource.insertAll(any()) } just Runs
    }

    @Test
    @DisplayName(
        "When 3rd page return list smaller then pageSize, " +
            "then should only invoke locatorRemoteDataSource.getBanks() 3 times",
    )
    fun testFetchBanks3rdPageSizeNotFull() =
        runTest(StandardTestDispatcher()) {
            coEvery {
                locatorRemoteDataSource.getBanks(
                    type = any(),
                    language = any(),
                    pageSize = any(),
                    offset = 0,
                )
            } returns (1..1000).map { mockk(relaxed = true) }

            coEvery {
                locatorRemoteDataSource.getBanks(
                    type = any(),
                    language = any(),
                    pageSize = any(),
                    offset = 1000,
                )
            } returns (1..1000).map { mockk(relaxed = true) }

            coEvery {
                locatorRemoteDataSource.getBanks(
                    type = any(),
                    language = any(),
                    pageSize = any(),
                    offset = 2000,
                )
            } returns (1..500).map { mockk(relaxed = true) }

            locatorRepositoryImpl.fetchBanks(
                type = LocatorRemoteDataSource.Type.ATM,
                language = "en",
                pageSize = 1000,
            )

            coVerify(exactly = 3) {
                locatorRemoteDataSource.getBanks(
                    type = LocatorRemoteDataSource.Type.ATM,
                    language = "en",
                    pageSize = 1000,
                    offset = any(),
                )
            }
        }
}

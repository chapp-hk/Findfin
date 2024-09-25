package ch.app.hk.bank.locator.feature.locator.data.remote.location

import ch.app.hk.bank.locator.core.location.client.LocationClient
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocationResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationRepositoryImpl unit tests")
class LocationRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locationClient = mockk<LocationClient>()

    private val locationRepository =
        LocationRepositoryImpl(
            ioDispatcher = testDispatcher,
            locationClient = locationClient,
        )

    @Test
    fun `getCurrentLocation() should get location from FusedLocationDataSource`() {
        runTest(testDispatcher) {
            coEvery { locationClient.getCurrentLocation() } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
        }
    }

    @Test
    fun `getCurrentLocation() should retry with FusedLocationDataSource when first attempt returns null`() {
        runTest(testDispatcher) {
            coEvery { locationClient.getCurrentLocation() } returnsMany
                listOf(
                    null,
                    mockk {
                        every { latitude } returns 1.0
                        every { longitude } returns 2.0
                    },
                )

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify(exactly = 2) { locationClient.getCurrentLocation() }
        }
    }
}

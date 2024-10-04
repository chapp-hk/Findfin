package org.chapp.findfin.feature.locator.data.remote.location

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.location.provider.LocationProvider
import org.chapp.findfin.core.location.provider.LocationProviderResult
import org.chapp.findfin.feature.locator.data.repo.model.LocationResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationRepositoryImpl unit tests")
class LocationRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locationProvider = mockk<LocationProvider>()

    private val locationRepository =
        LocationRepositoryImpl(
            ioDispatcher = testDispatcher,
            locationProvider = locationProvider,
        )

    @Test
    fun `getCurrentLocation() should return Location when location is available`() {
        runTest(testDispatcher) {
            val mockLocation =
                mockk<LocationProviderResult.Success> {
                    every { location.latitude } returns 1.0
                    every { location.longitude } returns 2.0
                }
            coEvery { locationProvider.getCurrentLocation() } returns mockLocation

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify { locationProvider.getCurrentLocation() }
        }
    }

    @Test
    fun `getCurrentLocation() should return UnknownError when location is unavailable`() {
        runTest(testDispatcher) {
            coEvery { locationProvider.getCurrentLocation() } returns
                LocationProviderResult.LocationUnavailable

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.UnknownError
            coVerify { locationProvider.getCurrentLocation() }
        }
    }

    @Test
    fun `getCurrentLocation() should return UnknownError on Error`() {
        runTest(testDispatcher) {
            coEvery { locationProvider.getCurrentLocation() } returns LocationProviderResult.Error

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.UnknownError
            coVerify { locationProvider.getCurrentLocation() }
        }
    }
}

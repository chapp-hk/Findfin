package org.chapp.findfin.core.location.provider

import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationToken
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.location.provider.api.Location
import org.chapp.findfin.core.location.provider.api.LocationResult
import org.chapp.findfin.core.location.provider.impl.LocationProviderManagerImpl
import org.chapp.findfin.testing.google.play.services.task.mockTaskResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import android.location.Location as AndroidLocation

@DisplayName("LocationProviderImpl unit tests")
class LocationProviderManagerImplTest {
    private val fusedLocationProviderClient = mockk<FusedLocationProviderClient>()

    private val locationProvider = LocationProviderManagerImpl(fusedLocationProviderClient)

    @Test
    fun `getCurrentLocation returns expected location`() {
        runTest(StandardTestDispatcher()) {
            val expectedLocation =
                mockk<AndroidLocation> {
                    every { latitude } returns 123.0
                    every { longitude } returns 456.0
                }

            every {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            } returns mockTaskResult(expectedLocation)

            locationProvider.getCurrentLocation() shouldBe
                LocationResult.Success(Location(123.0, 456.0))

            verify {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            }
        }
    }

    @Test
    fun `getCurrentLocation returns LocationUnavailable when no location update is received`() {
        runTest(StandardTestDispatcher()) {
            every {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            } returns mockTaskResult(null)

            locationProvider.getCurrentLocation() shouldBe
                LocationResult.LocationUnavailable
        }
    }

    @Test
    fun `getCurrentLocation returns Error on exception`() {
        runTest(StandardTestDispatcher()) {
            every {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            } throws Exception("Test exception")

            val result = locationProvider.getCurrentLocation()

            result shouldBe LocationResult.Error
        }
    }
}

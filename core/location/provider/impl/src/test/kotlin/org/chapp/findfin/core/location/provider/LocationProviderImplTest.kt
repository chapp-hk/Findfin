package org.chapp.findfin.core.location.provider

import android.location.Location
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationToken
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.location.provider.api.LocationProviderResult
import org.chapp.findfin.core.location.provider.api.Position
import org.chapp.findfin.core.location.provider.impl.LocationProviderImpl
import org.chapp.findfin.testing.google.play.services.task.mockTaskResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationProviderImpl unit tests")
class LocationProviderImplTest {
    private val fusedLocationProviderClient = mockk<FusedLocationProviderClient>()

    private val locationProvider = LocationProviderImpl(fusedLocationProviderClient)

    @Test
    fun `getCurrentLocation returns expected location`() {
        runTest(StandardTestDispatcher()) {
            val expectedLocation =
                mockk<Location> {
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
                LocationProviderResult.Success(Position(123.0, 456.0))

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
                LocationProviderResult.LocationUnavailable
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

            result shouldBe LocationProviderResult.Error
        }
    }
}

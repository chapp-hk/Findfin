package ch.app.hk.bank.locator.core.location.provider

import android.location.Location
import ch.app.hk.bank.locator.testing.google.play.services.task.mockTaskResult
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationToken
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationProviderImpl unit tests")
class LocationProviderImplTest {
    private val fusedLocationProviderClient = mockk<FusedLocationProviderClient>()

    private val locationProvider = LocationProviderImpl(fusedLocationProviderClient)

    @Test
    fun `getCurrentLocation returns expected location`() {
        runTest(StandardTestDispatcher()) {
            val expectedLocation = mockk<Location>()

            every {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            } returns mockTaskResult(expectedLocation)

            locationProvider.getCurrentLocation() shouldBe
                LocationProviderResult.Success(expectedLocation)

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

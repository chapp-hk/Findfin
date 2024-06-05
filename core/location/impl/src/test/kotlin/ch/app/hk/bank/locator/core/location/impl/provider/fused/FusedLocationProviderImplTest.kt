package ch.app.hk.bank.locator.core.location.impl.provider.fused

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

@DisplayName("FusedLocationDataSourceImpl unit tests")
class FusedLocationProviderImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val fusedLocationProviderClient = mockk<FusedLocationProviderClient>()

    private val fusedLocationDataSource =
        FusedLocationProviderImpl(
            ioDispatcher = testDispatcher,
            fusedLocationProviderClient,
        )

    @Test
    fun `getSingleCurrentLocation returns expected location`() {
        runTest(testDispatcher) {
            val expectedLocation = mockk<Location>()
            every {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            } returns mockTaskResult(expectedLocation)

            val result = fusedLocationDataSource.getSingleCurrentLocation()

            result shouldBe expectedLocation
            verify {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            }
        }
    }

    @Test
    fun `getSingleCurrentLocation returns null when no location update is received`() {
        runTest(testDispatcher) {
            every {
                fusedLocationProviderClient.getCurrentLocation(
                    any<CurrentLocationRequest>(),
                    any<CancellationToken>(),
                )
            } returns mockTaskResult(null)

            val result = fusedLocationDataSource.getSingleCurrentLocation()

            result shouldBe null
        }
    }
}

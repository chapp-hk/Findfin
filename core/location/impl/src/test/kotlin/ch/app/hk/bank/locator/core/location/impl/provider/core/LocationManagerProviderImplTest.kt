package ch.app.hk.bank.locator.core.location.impl.provider.core

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Thread.sleep

class LocationManagerProviderImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val context = mockk<Context>()
    private val locationManager = mockk<LocationManager>()

    private val locationManagerDataSource =
        LocationManagerProviderImpl(
            ioDispatcher = testDispatcher,
            context = context,
        )

    @BeforeEach
    fun setUp() {
        mockkStatic(ContextCompat::class)
        every {
            ContextCompat.getSystemService(
                any<Context>(),
                LocationManager::class.java,
            )
        } returns locationManager
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(ContextCompat::class)
    }

    @Test
    fun `getCurrentLocation returns expected location`() =
        runTest(testDispatcher) {
            val expectedLocation = mockk<Location>()
            coEvery {
                locationManager.requestLocationUpdates(
                    any<String>(),
                    any<Long>(),
                    any<Float>(),
                    any<LocationListener>(),
                )
            } answers {
                arg<LocationListener>(3).onLocationChanged(expectedLocation)
            }

            val result = locationManagerDataSource.getSingleCurrentLocation()

            result shouldBe expectedLocation
        }

    @Test
    fun `getCurrentLocation returns null when no location update is received within 5 seconds`() =
        runTest(testDispatcher) {
            coEvery {
                locationManager.requestLocationUpdates(
                    any<String>(),
                    any<Long>(),
                    any<Float>(),
                    any<LocationListener>(),
                )
            } answers {
                sleep(5000)
            }

            every {
                locationManager.removeUpdates(any<LocationListener>())
            } just Runs

            val result = locationManagerDataSource.getSingleCurrentLocation()

            result shouldBe null
            verify { locationManager.removeUpdates(any<LocationListener>()) }
        }
}

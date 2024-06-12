package ch.app.hk.bank.locator.core.location.impl.datasource.core

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
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

class CoreLocationDataSourceImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val context = mockk<Context>()
    private val locationManager = mockk<LocationManager>()

    private val locationManagerDataSource =
        CoreLocationDataSourceImpl(
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
            every {
                context.mainLooper
            } returns mockk()

            coEvery {
                locationManager.requestLocationUpdates(
                    any<String>(),
                    any<Long>(),
                    any<Float>(),
                    any<LocationListener>(),
                    any<Looper>(),
                )
            } answers {
                arg<LocationListener>(3).onLocationChanged(expectedLocation)
            }

            val result =
                locationManagerDataSource.getSingleCurrentLocation(
                    provider = CoreLocationProvider.GPS,
                    timeoutMillis = 5000L,
                )

            result shouldBe expectedLocation
        }

    @Test
    fun `getCurrentLocation returns null when no location update is received within timeout period`() =
        runTest(testDispatcher) {
            val timeoutMillis = 3000L

            every {
                context.mainLooper
            } returns mockk()

            coEvery {
                locationManager.requestLocationUpdates(
                    any<String>(),
                    any<Long>(),
                    any<Float>(),
                    any<LocationListener>(),
                    any<Looper>(),
                )
            } answers {
                sleep(timeoutMillis)
            }

            every {
                locationManager.removeUpdates(any<LocationListener>())
            } just Runs

            val result =
                locationManagerDataSource.getSingleCurrentLocation(
                    provider = CoreLocationProvider.GPS,
                    timeoutMillis = timeoutMillis,
                )

            result shouldBe null
            verify { locationManager.removeUpdates(any<LocationListener>()) }
        }
}

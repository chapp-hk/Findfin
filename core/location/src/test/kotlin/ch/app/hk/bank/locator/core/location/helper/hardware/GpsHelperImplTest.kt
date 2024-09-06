package ch.app.hk.bank.locator.core.location.helper.hardware

import android.content.Context
import android.location.LocationManager
import androidx.core.content.ContextCompat
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GpsRepositoryImpl unit tests")
class GpsHelperImplTest {
    private val context = mockk<Context>()
    private val locationManager = mockk<LocationManager>()

    private val gpsRepository = GpsHelperImpl(context)

    @BeforeEach
    fun setUp() {
        mockkStatic(ContextCompat::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkStatic(ContextCompat::class)
    }

    @Test
    fun `hasGpsSensor returns true when GPS sensor is present`() {
        every { context.packageManager.hasSystemFeature(any()) } returns true

        val result = gpsRepository.hasGpsSensor()

        result shouldBe true
    }

    @Test
    fun `hasGpsSensor returns false when GPS sensor is not present`() {
        every { context.packageManager.hasSystemFeature(any()) } returns false

        val result = gpsRepository.hasGpsSensor()

        result shouldBe false
    }

    @Test
    fun `isGpsEnabled returns true when GPS is present and enabled`() {
        every {
            ContextCompat.getSystemService(
                any<Context>(),
                LocationManager::class.java,
            )
        } returns locationManager
        every { locationManager.isProviderEnabled(any()) } returns true

        val result = gpsRepository.isGpsEnabled()

        result shouldBe true
    }

    @Test
    fun `isGpsEnabled returns false when GPS is present and not enabled`() {
        every {
            ContextCompat.getSystemService(
                any<Context>(),
                LocationManager::class.java,
            )
        } returns locationManager
        every { locationManager.isProviderEnabled(any()) } returns false

        val result = gpsRepository.isGpsEnabled()

        result shouldBe false
    }

    @Test
    fun `isGpsEnabled returns false when GPS is not present`() {
        every {
            ContextCompat.getSystemService(
                any<Context>(),
                LocationManager::class.java,
            )
        } returns null

        val result = gpsRepository.isGpsEnabled()

        result shouldBe false
    }
}

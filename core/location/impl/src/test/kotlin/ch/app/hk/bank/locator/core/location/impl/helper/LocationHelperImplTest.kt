package ch.app.hk.bank.locator.core.location.impl.helper

import ch.app.hk.bank.locator.core.location.api.model.LocationResult
import ch.app.hk.bank.locator.core.location.impl.provider.core.LocationManagerProvider
import ch.app.hk.bank.locator.core.location.impl.provider.fused.FusedLocationProvider
import ch.app.hk.bank.locator.core.location.impl.util.gms.GmsCheckUtil
import ch.app.hk.bank.locator.core.location.impl.util.hardware.GpsUtil
import ch.app.hk.bank.locator.core.location.impl.util.permission.PermissionUtil
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationRepositoryImpl unit tests")
class LocationHelperImplTest {
    private val permissionUtil = mockk<PermissionUtil>()
    private val gpsUtil = mockk<GpsUtil>()
    private val gmsCheckUtil = mockk<GmsCheckUtil>()
    private val locationManagerProvider = mockk<LocationManagerProvider>()
    private val fusedLocationProvider = mockk<FusedLocationProvider>()

    private val locationRepository =
        LocationHelperImpl(
            permissionUtil = permissionUtil,
            gpsUtil = gpsUtil,
            gmsCheckUtil = gmsCheckUtil,
            locationManagerProvider = locationManagerProvider,
            fusedLocationProvider = fusedLocationProvider,
        )

    @Test
    fun `getSingleCurrentLocation() should return PermissionNotGranted when permission is not granted`() {
        runTest {
            every { permissionUtil.checkPermission() } returns false

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.PermissionNotGranted
        }
    }

    @Test
    fun `getSingleCurrentLocation() should return GpsNotSupported when gps sensor is not supported`() {
        runTest {
            every { permissionUtil.checkPermission() } returns true
            every { gpsUtil.hasGpsSensor() } returns false

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.GpsNotSupported
        }
    }

    @Test
    fun `getSingleCurrentLocation() should return GpsIsOff when gps is disabled`() {
        runTest {
            every { permissionUtil.checkPermission() } returns true
            every { gpsUtil.hasGpsSensor() } returns true
            every { gpsUtil.isGpsEnabled() } returns false

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.GpsIsOff
        }
    }

    @Test
    fun `getSingleCurrentLocation() should get location from FusedLocationDataSource`() {
        runTest {
            every { permissionUtil.checkPermission() } returns true
            every { gpsUtil.hasGpsSensor() } returns true
            every { gpsUtil.isGpsEnabled() } returns true
            every { gmsCheckUtil.isGmsAvailable() } returns true
            coEvery { fusedLocationProvider.getSingleCurrentLocation() } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify { locationManagerProvider wasNot Called }
        }
    }

    @Test
    fun `getSingleCurrentLocation() should get location from LocationManagerDataSource`() {
        runTest {
            every { permissionUtil.checkPermission() } returns true
            every { gpsUtil.hasGpsSensor() } returns true
            every { gpsUtil.isGpsEnabled() } returns true
            every { gmsCheckUtil.isGmsAvailable() } returns false
            coEvery { locationManagerProvider.getSingleCurrentLocation() } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify { fusedLocationProvider wasNot Called }
        }
    }

    @Test
    fun `getSingleCurrentLocation() should return UnknownError when locationManagerDataSource return null`() {
        runTest {
            every { permissionUtil.checkPermission() } returns true
            every { gpsUtil.hasGpsSensor() } returns true
            every { gpsUtil.isGpsEnabled() } returns true
            every { gmsCheckUtil.isGmsAvailable() } returns false
            coEvery { locationManagerProvider.getSingleCurrentLocation() } returns null

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.UnknownError
        }
    }

    @Test
    fun `getSingleCurrentLocation() should return UnknownError when fusedLocationDataSource return null`() {
        runTest {
            every { permissionUtil.checkPermission() } returns true
            every { gpsUtil.hasGpsSensor() } returns true
            every { gpsUtil.isGpsEnabled() } returns true
            every { gmsCheckUtil.isGmsAvailable() } returns true
            coEvery { fusedLocationProvider.getSingleCurrentLocation() } returns null

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.UnknownError
        }
    }
}

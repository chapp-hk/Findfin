package ch.app.hk.bank.locator.core.location.impl.repo

import ch.app.hk.bank.locator.core.location.api.model.LocationResult
import ch.app.hk.bank.locator.core.location.impl.datasource.core.CoreLocationDataSource
import ch.app.hk.bank.locator.core.location.impl.datasource.core.CoreLocationProvider
import ch.app.hk.bank.locator.core.location.impl.datasource.fused.FusedLocationDataSource
import ch.app.hk.bank.locator.core.location.impl.helper.gms.GmsCheckHelper
import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.impl.helper.permission.PermissionHelper
import io.kotest.matchers.shouldBe
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationRepositoryImpl unit tests")
class LocationRepositoryImplTest {
    private val permissionHelper = mockk<PermissionHelper>()
    private val gpsHelper = mockk<GpsHelper>()
    private val gmsCheckHelper = mockk<GmsCheckHelper>()
    private val coreLocationDataSource = mockk<CoreLocationDataSource>()
    private val fusedLocationDataSource = mockk<FusedLocationDataSource>()

    private val locationRepository =
        LocationRepositoryImpl(
            permissionHelper = permissionHelper,
            gpsHelper = gpsHelper,
            gmsCheckHelper = gmsCheckHelper,
            coreLocationDataSource = coreLocationDataSource,
            fusedLocationDataSource = fusedLocationDataSource,
        )

    @Test
    fun `getSingleCurrentLocation() should return PermissionNotGranted when permission is not granted`() {
        runTest {
            every { permissionHelper.checkPermission() } returns false

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.PermissionNotGranted
        }
    }

    @Test
    fun `getSingleCurrentLocation() should return GpsNotSupported when gps sensor is not supported`() {
        runTest {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns false

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.GpsNotSupported
        }
    }

    @Test
    fun `getSingleCurrentLocation() should return GpsIsOff when gps is disabled`() {
        runTest {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns false

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.GpsIsOff
        }
    }

    @Test
    fun `getSingleCurrentLocation() should get location from FusedLocationDataSource`() {
        runTest {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns true
            every { gmsCheckHelper.isGmsAvailable() } returns true
            coEvery { fusedLocationDataSource.getSingleCurrentLocation() } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify { coreLocationDataSource wasNot Called }
        }
    }

    @Test
    fun `getSingleCurrentLocation() should get location from LocationManagerDataSource`() {
        runTest {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns true
            every { gmsCheckHelper.isGmsAvailable() } returns false
            coEvery { coreLocationDataSource.getSingleCurrentLocation(any(), any()) } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify { fusedLocationDataSource wasNot Called }
        }
    }

    @Test
    fun `getSingleCurrentLocation() should retry with NETWORK provider when first attempt with GPS provider returns null`() {
        runTest {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns true
            every { gmsCheckHelper.isGmsAvailable() } returns false
            coEvery { coreLocationDataSource.getSingleCurrentLocation(CoreLocationProvider.GPS, any()) } returns null
            coEvery { coreLocationDataSource.getSingleCurrentLocation(CoreLocationProvider.NETWORK, any()) } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerifyOrder {
                coreLocationDataSource.getSingleCurrentLocation(CoreLocationProvider.GPS, any())
                coreLocationDataSource.getSingleCurrentLocation(CoreLocationProvider.NETWORK, any())
            }
        }
    }

    @Test
    fun `getSingleCurrentLocation() should retry with FusedLocationDataSource when first attempt returns null`() {
        runTest {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns true
            every { gmsCheckHelper.isGmsAvailable() } returns true
            coEvery { fusedLocationDataSource.getSingleCurrentLocation() } returnsMany
                listOf(
                    null,
                    mockk {
                        every { latitude } returns 1.0
                        every { longitude } returns 2.0
                    },
                )

            val result = locationRepository.getSingleCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify(exactly = 2) { fusedLocationDataSource.getSingleCurrentLocation() }
        }
    }
}

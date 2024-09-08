package ch.app.hk.bank.locator.feature.locator.data.remote.location

import ch.app.hk.bank.locator.core.location.client.LocationClient
import ch.app.hk.bank.locator.core.location.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.helper.permission.PermissionHelper
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocationResult
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LocationRepositoryImpl unit tests")
class LocationRepositoryImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val permissionHelper = mockk<PermissionHelper>()
    private val gpsHelper = mockk<GpsHelper>()
    private val locationClient = mockk<LocationClient>()

    private val locationRepository =
        LocationRepositoryImpl(
            ioDispatcher = testDispatcher,
            permissionHelper = permissionHelper,
            gpsHelper = gpsHelper,
            locationClient = locationClient,
        )

    @Test
    fun `getCurrentLocation() should return PermissionNotGranted when permission is not granted`() {
        runTest(testDispatcher) {
            every { permissionHelper.checkPermission() } returns false

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.PermissionNotGranted
        }
    }

    @Test
    fun `getCurrentLocation() should return GpsNotSupported when gps sensor is not supported`() {
        runTest(testDispatcher) {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns false

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.GpsNotSupported
        }
    }

    @Test
    fun `getCurrentLocation() should return GpsIsOff when gps is disabled`() {
        runTest(testDispatcher) {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns false

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.GpsIsOff
        }
    }

    @Test
    fun `getCurrentLocation() should get location from FusedLocationDataSource`() {
        runTest(testDispatcher) {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns true
            coEvery { locationClient.getCurrentLocation() } returns
                mockk {
                    every { latitude } returns 1.0
                    every { longitude } returns 2.0
                }

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
        }
    }

    @Test
    fun `getCurrentLocation() should retry with FusedLocationDataSource when first attempt returns null`() {
        runTest(testDispatcher) {
            every { permissionHelper.checkPermission() } returns true
            every { gpsHelper.hasGpsSensor() } returns true
            every { gpsHelper.isGpsEnabled() } returns true
            coEvery { locationClient.getCurrentLocation() } returnsMany
                listOf(
                    null,
                    mockk {
                        every { latitude } returns 1.0
                        every { longitude } returns 2.0
                    },
                )

            val result = locationRepository.getCurrentLocation()

            result shouldBe LocationResult.Location(1.0, 2.0)
            coVerify(exactly = 2) { locationClient.getCurrentLocation() }
        }
    }
}

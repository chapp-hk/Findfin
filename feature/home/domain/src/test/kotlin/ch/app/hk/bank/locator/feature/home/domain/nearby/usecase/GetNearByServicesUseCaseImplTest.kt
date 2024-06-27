package ch.app.hk.bank.locator.feature.home.domain.nearby.usecase

import ch.app.hk.bank.locator.core.location.api.model.LocationResult
import ch.app.hk.bank.locator.core.location.api.repo.LocationRepository
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult
import ch.app.hk.bank.locator.feature.locator.data.repo.repository.LocatorRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GetNearByServicesUseCaseImpl unit tests")
class GetNearByServicesUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locationRepository = mockk<LocationRepository>()
    private val locatorRepository = mockk<LocatorRepository>()

    private val getNearByServiceUseCase =
        GetNearByServicesUseCaseImpl(
            defaultDispatcher = testDispatcher,
            locationRepository = locationRepository,
            locatorRepository = locatorRepository,
        )

    @Test
    fun `invoke should return GpsIsOff when LocationResult is GpsIsOff`() =
        runTest(testDispatcher) {
            coEvery { locationRepository.getSingleCurrentLocation() } returns
                LocationResult.GpsIsOff

            val result = getNearByServiceUseCase()

            result shouldBe NearByResult.GpsIsOff
        }

    @Test
    fun `invoke should return GpsNotSupported when LocationResult is GpsNotSupported`() =
        runTest(testDispatcher) {
            coEvery { locationRepository.getSingleCurrentLocation() } returns
                LocationResult.GpsNotSupported

            val result = getNearByServiceUseCase()

            result shouldBe NearByResult.GpsNotSupported
        }

    @Test
    fun `invoke should return PermissionNotGranted when LocationResult is PermissionNotGranted`() =
        runTest(testDispatcher) {
            coEvery { locationRepository.getSingleCurrentLocation() } returns
                LocationResult.PermissionNotGranted

            val result = getNearByServiceUseCase()

            result shouldBe NearByResult.PermissionNotGranted
        }

    @Test
    fun `invoke should return UnknownError when LocationResult is UnknownError`() =
        runTest(testDispatcher) {
            coEvery { locationRepository.getSingleCurrentLocation() } returns
                LocationResult.UnknownError

            val result = getNearByServiceUseCase()

            result shouldBe NearByResult.UnknownError
        }

    @Test
    fun `invoke should return Location when LocationResult is Location`() =
        runTest(testDispatcher) {
            // Arrange
            val mockLocation = LocationResult.Location(1.0, 1.0)

            coEvery { locationRepository.getSingleCurrentLocation() } returns
                mockLocation
            coEvery { locatorRepository.getLocatorsWithinBound(any(), any(), any(), any()) } returns
                listOf(mockk(relaxed = true))

            // Act
            val result = getNearByServiceUseCase()

            // Assert
            result
                .shouldBeInstanceOf<NearByResult.Location>()
                .list.size shouldBe 1
        }
}

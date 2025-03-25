package org.chapp.findfin.feature.home.domain.nearby.usecase

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult
import org.chapp.findfin.feature.locator.data.repo.model.LocationResult
import org.chapp.findfin.feature.locator.data.repo.repo.LocationRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GetNearByServicesUseCaseImpl unit tests")
class GetNearByServicesUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locationRepository = mockk<LocationRepository>()
    private val bankLocationRepository = mockk<BankLocationRepository>()

    private val getNearByServiceUseCase =
        GetNearByServicesUseCaseImpl(
            defaultDispatcher = testDispatcher,
            locationRepository = locationRepository,
            bankLocationRepository = bankLocationRepository,
        )

    @Test
    fun `invoke should return UnknownError when LocationResult is UnknownError`() =
        runTest(testDispatcher) {
            coEvery { locationRepository.getCurrentLocation() } returns
                LocationResult.UnknownError

            val result = getNearByServiceUseCase(language = "en")

            result shouldBe NearByResult.UnknownError
        }

    @Test
    fun `invoke should return Location when LocationResult is Location`() =
        runTest(testDispatcher) {
            val mockLocation = LocationResult.Location(1.0, 1.0)

            coEvery { locationRepository.getCurrentLocation() } returns
                mockLocation
            coEvery {
                bankLocationRepository.getLocationsWithinBound(
                    language = any(),
                    bound = any(),
                )
            } returns listOf(mockk(relaxed = true))

            val result = getNearByServiceUseCase(language = "en")

            result
                .shouldBeInstanceOf<NearByResult.Location>()
                .list.size shouldBe 1
        }
}

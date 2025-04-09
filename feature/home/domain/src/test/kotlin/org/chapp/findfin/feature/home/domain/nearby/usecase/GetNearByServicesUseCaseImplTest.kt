package org.chapp.findfin.feature.home.domain.nearby.usecase

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.location.provider.api.LocationProvider
import org.chapp.findfin.core.location.provider.api.LocationProviderResult
import org.chapp.findfin.core.location.provider.api.Position
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GetNearByServicesUseCaseImpl unit tests")
class GetNearByServicesUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locationProvider = mockk<LocationProvider>()
    private val bankLocationRepository = mockk<BankLocationRepository>()

    private val getNearByServiceUseCase =
        GetNearByServicesUseCaseImpl(
            defaultDispatcher = testDispatcher,
            locationRepository = locationProvider,
            bankLocationRepository = bankLocationRepository,
        )

    @Test
    fun `invoke should return UnknownError when LocationResult is UnknownError`() =
        runTest(testDispatcher) {
            coEvery { locationProvider.getCurrentLocation() } returns
                LocationProviderResult.Error

            val result = getNearByServiceUseCase(language = "en")

            result shouldBe NearByResult.UnknownError
        }

    @Test
    fun `invoke should return Location when LocationResult is Location`() =
        runTest(testDispatcher) {
            val mockLocation = LocationProviderResult.Success(Position(1.0, 1.0))

            coEvery { locationProvider.getCurrentLocation() } returns
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

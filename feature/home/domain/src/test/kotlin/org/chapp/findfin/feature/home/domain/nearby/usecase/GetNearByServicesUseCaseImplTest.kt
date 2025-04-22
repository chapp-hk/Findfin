package org.chapp.findfin.feature.home.domain.nearby.usecase

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.chapp.findfin.core.location.provider.api.Location
import org.chapp.findfin.core.location.provider.api.LocationProviderManager
import org.chapp.findfin.core.location.provider.api.LocationResult
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("GetNearByServicesUseCaseImpl unit tests")
class GetNearByServicesUseCaseImplTest {
    private val testDispatcher = StandardTestDispatcher()
    private val locationProviderManager = mockk<LocationProviderManager>()
    private val bankRepository = mockk<BankRepository>()

    private val getNearByServiceUseCase =
        GetNearByServicesUseCaseImpl(
            defaultDispatcher = testDispatcher,
            locationProviderManager = locationProviderManager,
            bankRepository = bankRepository,
        )

    @Test
    fun `invoke should return UnknownError when LocationResult is UnknownError`() =
        runTest(testDispatcher) {
            coEvery { locationProviderManager.getCurrentLocation() } returns
                LocationResult.Error

            val result = getNearByServiceUseCase()

            result shouldBe NearByResult.UnknownError
        }

    @Test
    fun `invoke should return Location when LocationResult is Location`() =
        runTest(testDispatcher) {
            val mockLocation = LocationResult.Success(Location(1.0, 1.0))

            coEvery { locationProviderManager.getCurrentLocation() } returns
                mockLocation
            coEvery {
                bankRepository.getBanksWithinBound(bound = any())
            } returns listOf(mockk(relaxed = true))

            val result = getNearByServiceUseCase()

            result
                .shouldBeInstanceOf<NearByResult.Location>()
                .list.size shouldBe 1
        }
}

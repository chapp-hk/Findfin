package org.chapp.findfin.feature.home.domain.nearby.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.location.provider.api.LocationProviderManager
import org.chapp.findfin.core.location.provider.api.LocationResult
import org.chapp.findfin.core.threading.DispatcherDefault
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.chapp.findfin.feature.home.domain.nearby.mapper.ServiceMapper
import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import org.mapstruct.factory.Mappers
import javax.inject.Inject
import kotlin.math.cos

@HiltWrapBindModule
internal class GetNearByServicesUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val locationProviderManager: LocationProviderManager,
    private val bankRepository: BankRepository,
) : GetNearByServicesUseCase {
    override suspend fun invoke(): NearByResult {
        return withContext(defaultDispatcher) {
            when (val locationResult = locationProviderManager.getCurrentLocation()) {
                LocationResult.Error, LocationResult.LocationUnavailable -> {
                    NearByResult.UnknownError
                }

                is LocationResult.Success -> {
                    val mapper = Mappers.getMapper(ServiceMapper::class.java)

                    val boundingBox =
                        calculateBoundingBox(
                            latitude = locationResult.location.latitude,
                            longitude = locationResult.location.longitude,
                        )

                    val list =
                        bankRepository.getBanksByParameters(bound = boundingBox)

                    NearByResult.Location(list.map(mapper::clone))
                }
            }
        }
    }

    /**
     * Calculates a bounding box with boundaries smaller than 1km from the center latitude and longitude.
     *
     * @param latitude The latitude of the center point.
     * @param longitude The longitude of the center point.
     *
     * @return A LocationBound object representing the bounding box. The bounding box is a square area
     *         centered at the given latitude and longitude, with sides of length 2*radius.
     *
     * The function uses the Haversine formula to calculate the latitude and longitude changes for the given radius.
     * It then calculates the minimum and maximum latitude and longitude for the bounding box.
     *
     * The value `111.045` is used to convert degrees of latitude to kilometers.
     * This is based on the average radius of the Earth, which is approximately 6,371 kilometers.
     * One degree of latitude is approximately equal to `111.045` kilometers on the Earth's surface.
     * This is a commonly used conversion factor in geographic calculations.
     */
    private fun calculateBoundingBox(
        latitude: Double,
        longitude: Double,
    ): BankLocationBound {
        val latChange = BOUND_RADIUS / KILOMETERS_PER_DEGREE
        val lonChange = BOUND_RADIUS / (KILOMETERS_PER_DEGREE * cos(Math.toRadians(latitude)))

        return BankLocationBound(
            minLatitude = latitude - latChange,
            maxLatitude = latitude + latChange,
            minLongitude = longitude - lonChange,
            maxLongitude = longitude + lonChange,
        )
    }

    companion object {
        private const val BOUND_RADIUS = 1.0
        private const val KILOMETERS_PER_DEGREE = 111.045
    }
}

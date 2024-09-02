package ch.app.hk.bank.locator.feature.home.domain.nearby.usecase

import ch.app.hk.bank.locator.core.location.api.model.LocationResult
import ch.app.hk.bank.locator.core.location.api.repo.LocationRepository
import ch.app.hk.bank.locator.core.threading.DispatcherDefault
import ch.app.hk.bank.locator.feature.bank.data.repo.location.model.BankLocationBound
import ch.app.hk.bank.locator.feature.bank.data.repo.location.repository.BankLocationRepository
import ch.app.hk.bank.locator.feature.home.domain.nearby.mapper.ServiceMapper
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.mapstruct.factory.Mappers
import javax.inject.Inject
import kotlin.math.cos

@HiltWrapBindModule
internal class GetNearByServicesUseCaseImpl @Inject constructor(
    @DispatcherDefault private val defaultDispatcher: CoroutineDispatcher,
    private val locationRepository: LocationRepository,
    private val bankLocationRepository: BankLocationRepository,
) : GetNearByServicesUseCase {
    override suspend fun invoke(): NearByResult {
        return withContext(defaultDispatcher) {
            when (val locationResult = locationRepository.getSingleCurrentLocation()) {
                LocationResult.GpsIsOff -> NearByResult.GpsIsOff
                LocationResult.GpsNotSupported -> NearByResult.GpsNotSupported
                LocationResult.PermissionNotGranted -> NearByResult.PermissionNotGranted
                LocationResult.UnknownError -> NearByResult.UnknownError
                is LocationResult.Location -> {
                    val mapper = Mappers.getMapper(ServiceMapper::class.java)

                    val boundingBox =
                        calculateBoundingBox(
                            latitude = locationResult.lat,
                            longitude = locationResult.lon,
                        )

                    val list = bankLocationRepository.getLocatorsWithinBound(boundingBox)

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
            minLat = latitude - latChange,
            maxLat = latitude + latChange,
            minLong = longitude - lonChange,
            maxLong = longitude + lonChange,
        )
    }

    companion object {
        private const val BOUND_RADIUS = 1.0
        private const val KILOMETERS_PER_DEGREE = 111.045
    }
}

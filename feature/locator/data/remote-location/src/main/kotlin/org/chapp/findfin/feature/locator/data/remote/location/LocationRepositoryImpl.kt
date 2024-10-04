package org.chapp.findfin.feature.locator.data.remote.location

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.chapp.findfin.core.location.provider.LocationProvider
import org.chapp.findfin.core.location.provider.LocationProviderResult
import org.chapp.findfin.core.logging.appLogger
import org.chapp.findfin.core.threading.DispatcherIo
import org.chapp.findfin.feature.locator.data.repo.model.LocationResult
import org.chapp.findfin.feature.locator.data.repo.repo.LocationRepository
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class LocationRepositoryImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val locationProvider: LocationProvider,
) : LocationRepository {
    override suspend fun getCurrentLocation(): LocationResult {
        return withContext(ioDispatcher) {
            when (val result = locationProvider.getCurrentLocation()) {
                LocationProviderResult.Error,
                LocationProviderResult.LocationUnavailable,
                -> LocationResult.UnknownError

                is LocationProviderResult.Success -> {
                    appLogger.debug(
                        tag = javaClass.simpleName,
                        message =
                            "latitude: ${result.location.latitude}, " +
                                "longitude: ${result.location.longitude}",
                    )

                    LocationResult.Location(
                        lat = result.location.latitude,
                        lon = result.location.longitude,
                    )
                }
            }
        }
    }
}

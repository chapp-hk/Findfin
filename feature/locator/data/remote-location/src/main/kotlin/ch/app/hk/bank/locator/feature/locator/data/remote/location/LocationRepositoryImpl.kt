package ch.app.hk.bank.locator.feature.locator.data.remote.location

import ch.app.hk.bank.locator.core.location.provider.LocationProvider
import ch.app.hk.bank.locator.core.location.provider.LocationProviderResult
import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocationResult
import ch.app.hk.bank.locator.feature.locator.data.repo.repo.LocationRepository
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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

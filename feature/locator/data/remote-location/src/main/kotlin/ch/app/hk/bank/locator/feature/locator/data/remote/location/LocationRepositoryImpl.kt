package ch.app.hk.bank.locator.feature.locator.data.remote.location

import android.location.Location
import ch.app.hk.bank.locator.core.location.client.LocationClient
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
    private val locationClient: LocationClient,
) : LocationRepository {
    override suspend fun getCurrentLocation(): LocationResult {
        return withContext(ioDispatcher) {
            val location = internalGetCurrentLocation()

            appLogger.debug(
                tag = javaClass.simpleName,
                message = "latitude: ${location.latitude}, longitude: ${location.longitude}",
            )

            LocationResult.Location(location.latitude, location.longitude)
        }
    }

    private suspend fun internalGetCurrentLocation(): Location {
        appLogger.debug(
            tag = javaClass.simpleName,
            message = "getting location...",
        )

        val location = locationClient.getCurrentLocation()
        return location ?: internalGetCurrentLocation()
    }
}

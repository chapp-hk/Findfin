package ch.app.hk.bank.locator.feature.locator.data.remote.location

import android.location.Location
import ch.app.hk.bank.locator.core.location.client.LocationClient
import ch.app.hk.bank.locator.core.location.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.helper.permission.PermissionHelper
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
    private val permissionHelper: PermissionHelper,
    private val gpsHelper: GpsHelper,
    private val locationClient: LocationClient,
) : LocationRepository {
    override suspend fun getSingleCurrentLocation(): LocationResult {
        return withContext(ioDispatcher) {
            if (!permissionHelper.checkPermission()) {
                appLogger.debug(tag = javaClass.simpleName, message = "PermissionNotGranted")
                LocationResult.PermissionNotGranted
            } else if (!gpsHelper.hasGpsSensor()) {
                appLogger.debug(tag = javaClass.simpleName, message = "GpsNotSupported")
                LocationResult.GpsNotSupported
            } else if (!gpsHelper.isGpsEnabled()) {
                appLogger.debug(tag = javaClass.simpleName, message = "GpsIsOff")
                LocationResult.GpsIsOff
            } else {
                val location = getCurrentLocation()

                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "latitude: ${location.latitude}, longitude: ${location.longitude}",
                )

                LocationResult.Location(location.latitude, location.longitude)
            }
        }
    }

    private suspend fun getCurrentLocation(): Location {
        val location = getCurrentLocationFromGms()
        return location ?: getCurrentLocation()
    }

    private suspend fun getCurrentLocationFromGms(): Location? {
        appLogger.debug(
            tag = javaClass.simpleName,
            message = "getting location from gms...",
        )

        return locationClient.getSingleCurrentLocation()
    }
}

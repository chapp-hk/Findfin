package ch.app.hk.bank.locator.core.location.impl.repo

import android.location.Location
import ch.app.hk.bank.locator.core.location.api.model.LocationResult
import ch.app.hk.bank.locator.core.location.api.repo.LocationRepository
import ch.app.hk.bank.locator.core.location.impl.datasource.fused.FusedLocationDataSource
import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.impl.helper.permission.PermissionHelper
import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

@HiltWrapBindModule
internal class LocationRepositoryImpl @Inject constructor(
    private val permissionHelper: PermissionHelper,
    private val gpsHelper: GpsHelper,
    private val fusedLocationDataSource: FusedLocationDataSource,
) : LocationRepository {
    override suspend fun getSingleCurrentLocation(): LocationResult {
        return if (!permissionHelper.checkPermission()) {
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

    private suspend fun getCurrentLocation(): Location {
        val location = getCurrentLocationFromGms()
        return location ?: getCurrentLocation()
    }

    private suspend fun getCurrentLocationFromGms(): Location? {
        appLogger.debug(
            tag = javaClass.simpleName,
            message = "getting location from gms...",
        )

        return fusedLocationDataSource.getSingleCurrentLocation()
    }
}

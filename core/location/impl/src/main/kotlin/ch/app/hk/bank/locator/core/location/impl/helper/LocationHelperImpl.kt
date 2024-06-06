package ch.app.hk.bank.locator.core.location.impl.helper

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.location.api.help.LocationHelper
import ch.app.hk.bank.locator.core.location.api.model.LocationResult
import ch.app.hk.bank.locator.core.location.impl.provider.core.LocationManagerProvider
import ch.app.hk.bank.locator.core.location.impl.provider.fused.FusedLocationProvider
import ch.app.hk.bank.locator.core.location.impl.util.gms.GmsCheckUtil
import ch.app.hk.bank.locator.core.location.impl.util.hardware.GpsUtil
import ch.app.hk.bank.locator.core.location.impl.util.permission.PermissionUtil
import ch.app.hk.bank.locator.core.logging.appLogger
import javax.inject.Inject

@HiltExtBindModule
internal class LocationHelperImpl @Inject constructor(
    private val permissionUtil: PermissionUtil,
    private val gpsUtil: GpsUtil,
    private val gmsCheckUtil: GmsCheckUtil,
    private val locationManagerProvider: LocationManagerProvider,
    private val fusedLocationProvider: FusedLocationProvider,
) : LocationHelper {
    override suspend fun getSingleCurrentLocation(): LocationResult {
        return if (!permissionUtil.checkPermission()) {
            appLogger.debug(tag = javaClass.simpleName, message = "PermissionNotGranted")
            LocationResult.PermissionNotGranted
        } else if (!gpsUtil.hasGpsSensor()) {
            appLogger.debug(tag = javaClass.simpleName, message = "GpsNotSupported")
            LocationResult.GpsNotSupported
        } else if (!gpsUtil.isGpsEnabled()) {
            appLogger.debug(tag = javaClass.simpleName, message = "GpsIsOff")
            LocationResult.GpsIsOff
        } else {
            val location =
                if (gmsCheckUtil.isGmsAvailable()) {
                    appLogger.debug(tag = javaClass.simpleName, message = "gms available")
                    fusedLocationProvider.getSingleCurrentLocation()
                } else {
                    appLogger.debug(tag = javaClass.simpleName, message = "gms not available")
                    locationManagerProvider.getSingleCurrentLocation()
                }

            if (location == null) {
                appLogger.debug(tag = javaClass.simpleName, message = "UnknownError")
                LocationResult.UnknownError
            } else {
                appLogger.debug(
                    tag = javaClass.simpleName,
                    message = "latitude: ${location.latitude}, longitude: ${location.longitude}",
                )
                LocationResult.Location(location.latitude, location.longitude)
            }
        }
    }
}

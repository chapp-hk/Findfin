package org.chapp.findfin.core.location.provider.impl

import androidx.annotation.RequiresPermission
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import org.chapp.findfin.core.location.provider.api.Location
import org.chapp.findfin.core.location.provider.api.LocationProviderManager
import org.chapp.findfin.core.location.provider.api.LocationResult
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of the [LocationProviderManager] interface that provides location data using the
 * [FusedLocationProviderClient].
 *
 * @property fusedLocationProviderClient The client used to access location data.
 */
@Singleton
@HiltWrapBindModule
internal class LocationProviderManagerImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : LocationProviderManager {
    /**
     * Retrieves the current location of the device.
     *
     * This method requires either `ACCESS_FINE_LOCATION` or `ACCESS_COARSE_LOCATION` permission.
     *
     * @return [LocationResult] which can be a success with the location data,
     * an error, or an indication that the location is unavailable.
     */
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getCurrentLocation(): LocationResult {
        val request =
            CurrentLocationRequest
                .Builder()
                .build()

        val cancellationTokenSource = CancellationTokenSource()

        return runCatching {
            val androidLocation =
                fusedLocationProviderClient
                    .getCurrentLocation(request, cancellationTokenSource.token)
                    .await()

            if (androidLocation != null) {
                val location =
                    Location(
                        latitude = androidLocation.latitude,
                        longitude = androidLocation.longitude,
                    )
                LocationResult.Success(location)
            } else {
                LocationResult.LocationUnavailable
            }
        }.also {
            cancellationTokenSource.cancel()
        }.getOrElse {
            LocationResult.Error
        }
    }
}

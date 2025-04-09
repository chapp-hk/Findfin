package org.chapp.findfin.core.location.provider.impl

import androidx.annotation.RequiresPermission
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import org.chapp.findfin.core.location.provider.api.LocationProvider
import org.chapp.findfin.core.location.provider.api.LocationProviderResult
import org.chapp.findfin.core.location.provider.api.Position
import org.chapp.library.hiltwrap.annotation.HiltWrapBindModule
import javax.inject.Inject

/**
 * Implementation of the [LocationProvider] interface that provides location data using the
 * [FusedLocationProviderClient].
 *
 * @property fusedLocationProviderClient The client used to access location data.
 */
@HiltWrapBindModule
internal class LocationProviderImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : LocationProvider {
    /**
     * Retrieves the current location of the device.
     *
     * This method requires either `ACCESS_FINE_LOCATION` or `ACCESS_COARSE_LOCATION` permission.
     *
     * @return [LocationProviderResult] which can be a success with the location data,
     * an error, or an indication that the location is unavailable.
     */
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getCurrentLocation(): LocationProviderResult {
        val request =
            CurrentLocationRequest
                .Builder()
                .build()

        val cancellationTokenSource = CancellationTokenSource()

        return runCatching {
            val location =
                fusedLocationProviderClient
                    .getCurrentLocation(request, cancellationTokenSource.token)
                    .await()

            if (location != null) {
                val position =
                    Position(latitude = location.latitude, longitude = location.longitude)
                LocationProviderResult.Success(position)
            } else {
                LocationProviderResult.LocationUnavailable
            }
        }.also {
            cancellationTokenSource.cancel()
        }.getOrElse {
            LocationProviderResult.Error
        }
    }
}

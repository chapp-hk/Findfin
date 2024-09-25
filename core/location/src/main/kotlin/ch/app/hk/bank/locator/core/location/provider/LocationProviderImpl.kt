package ch.app.hk.bank.locator.core.location.provider

import android.location.Location
import androidx.annotation.RequiresPermission
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
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
     * @return The current [Location] of the device, or `null` if the location could not be determined.
     */
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getCurrentLocation(): Location? {
        val request =
            CurrentLocationRequest
                .Builder()
                .build()

        val cancellationTokenSource = CancellationTokenSource()

        return fusedLocationProviderClient
            .getCurrentLocation(request, cancellationTokenSource.token)
            .await(cancellationTokenSource)
    }
}

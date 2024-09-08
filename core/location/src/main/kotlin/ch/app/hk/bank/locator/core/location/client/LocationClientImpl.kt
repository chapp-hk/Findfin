package ch.app.hk.bank.locator.core.location.client

import android.location.Location
import androidx.annotation.RequiresPermission
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltWrapBindModule
internal class LocationClientImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : LocationClient {
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getSingleCurrentLocation(): Location? {
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

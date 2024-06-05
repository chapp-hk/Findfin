package ch.app.hk.bank.locator.core.location.impl.provider.fused

import android.location.Location
import androidx.annotation.RequiresPermission
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
class FusedLocationProviderImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : FusedLocationProvider {
    @RequiresPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun getSingleCurrentLocation(): Location? =
        withContext(ioDispatcher) {
            val request =
                CurrentLocationRequest
                    .Builder()
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .setDurationMillis(5000L)
                    .build()

            val cancellationTokenSource = CancellationTokenSource()

            fusedLocationProviderClient
                .getCurrentLocation(request, cancellationTokenSource.token)
                .await(cancellationTokenSource)
        }
}

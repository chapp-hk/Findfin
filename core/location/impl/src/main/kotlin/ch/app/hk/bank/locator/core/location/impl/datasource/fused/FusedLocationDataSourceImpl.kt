package ch.app.hk.bank.locator.core.location.impl.datasource.fused

import android.location.Location
import androidx.annotation.RequiresPermission
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.library.hiltwrap.annotation.HiltExtBindModule
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltExtBindModule
internal class FusedLocationDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : FusedLocationDataSource {
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getSingleCurrentLocation(): Location? =
        withContext(ioDispatcher) {
            val request =
                CurrentLocationRequest
                    .Builder()
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .setDurationMillis(TIMEOUT)
                    .build()

            val cancellationTokenSource = CancellationTokenSource()

            fusedLocationProviderClient
                .getCurrentLocation(request, cancellationTokenSource.token)
                .await(cancellationTokenSource)
        }

    companion object {
        private const val TIMEOUT = 5000L
    }
}

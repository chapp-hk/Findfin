package ch.app.hk.bank.locator.core.location.impl.provider.core

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltExtBindModule
internal class CoreLocationProviderImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : CoreLocationProvider {
    @RequiresPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun getSingleCurrentLocation(): Location? =
        withTimeoutOrNull(timeMillis = 5000L) {
            withContext(ioDispatcher) {
                suspendCancellableCoroutine { continuation ->
                    val locationManager =
                        requireNotNull(
                            ContextCompat.getSystemService(
                                context,
                                LocationManager::class.java,
                            ),
                        )

                    val locationListener =
                        object : LocationListener {
                            override fun onLocationChanged(location: Location) {
                                continuation.resume(location) {
                                    locationManager.removeUpdates(this)
                                }
                            }
                        }

                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L,
                        0f,
                        locationListener,
                    )

                    continuation.invokeOnCancellation {
                        locationManager.removeUpdates(locationListener)
                    }
                }
            }
        }
}

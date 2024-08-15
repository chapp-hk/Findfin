package ch.app.hk.bank.locator.core.location.impl.datasource.core

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import ch.app.hk.bank.locator.core.logging.appLogger
import ch.app.hk.bank.locator.core.threading.DispatcherIo
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

@HiltWrapBindModule
internal class CoreLocationDataSourceImpl @Inject constructor(
    @DispatcherIo private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : CoreLocationDataSource {
    @RequiresPermission(
        anyOf = [
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ],
    )
    override suspend fun getSingleCurrentLocation(
        provider: CoreLocationProvider,
        timeoutMillis: Long,
    ): Location? =
        withTimeoutOrNull(timeMillis = timeoutMillis) {
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
                                if (continuation.isActive) {
                                    continuation.resume(location) {
                                        appLogger.debug(
                                            tag = javaClass.simpleName,
                                            message = "continuation.resume: $it",
                                        )
                                        locationManager.removeUpdates(this)
                                    }
                                }
                            }
                        }

                    locationManager.requestLocationUpdates(
                        provider.value,
                        0L,
                        0f,
                        locationListener,
                        context.mainLooper,
                    )

                    continuation.invokeOnCancellation {
                        appLogger.debug(
                            tag = javaClass.simpleName,
                            message = "continuation.invokeOnCancellation: $it",
                        )
                        locationManager.removeUpdates(locationListener)
                    }
                }
            }
        }
}

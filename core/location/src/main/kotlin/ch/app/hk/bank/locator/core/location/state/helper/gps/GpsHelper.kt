package ch.app.hk.bank.locator.core.location.state.helper.gps

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat

/**
 * Implementation of the [GpsHelper] interface that provides GPS-related functionalities.
 *
 * @property context The application context used to access system services.
 */
internal class GpsHelper(private val context: Context) {
    /**
     * Checks if the device has a GPS sensor.
     *
     * @return `true` if the device has a GPS sensor, `false` otherwise.
     */
    fun hasGpsSensor(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
    }

    /**
     * Checks if the GPS is enabled on the device.
     *
     * @return `true` if the GPS is enabled, `false` otherwise.
     */
    fun isGpsEnabled(): Boolean {
        return runCatching {
            ContextCompat.getSystemService(
                context,
                LocationManager::class.java,
            )!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }.getOrElse {
            false
        }
    }
}

package ch.app.hk.bank.locator.core.location.helper.hardware

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Implementation of the [GpsHelper] interface that provides GPS-related functionalities.
 *
 * @property context The application context used to access system services.
 */
@HiltWrapBindModule
internal class GpsHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : GpsHelper {
    /**
     * Checks if the device has a GPS sensor.
     *
     * @return `true` if the device has a GPS sensor, `false` otherwise.
     */
    override fun hasGpsSensor(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
    }

    /**
     * Checks if the GPS is enabled on the device.
     *
     * @return `true` if the GPS is enabled, `false` otherwise.
     */
    override fun isGpsEnabled(): Boolean {
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

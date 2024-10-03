package ch.app.hk.bank.locator.core.location.setting.helper

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.result.IntentSenderRequest
import androidx.core.content.ContextCompat
import ch.app.hk.bank.locator.core.location.setting.state.LocationSettingStatus
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import kotlinx.coroutines.tasks.await

/**
 * Helper class to manage location settings and provide the current location setting status.
 *
 * @property context The application context.
 */
internal class SettingHelperImpl(
    private val context: Context,
) : SettingHelper {
    /**
     * Gets the current location setting status.
     *
     * @return The current [LocationSettingStatus].
     */
    override fun getSettings(): LocationSettingStatus {
        return when {
            !context.hasGpsSensor() -> LocationSettingStatus.NoSensor
            !context.isGpsEnabled() -> LocationSettingStatus.Disabled
            else -> LocationSettingStatus.Enabled
        }
    }

    /**
     * Gets an [IntentSenderRequest] to prompt the user to enable location settings if needed.
     *
     * @return An [IntentSenderRequest] if the location settings need to be enabled,
     *         or null if they are already enabled.
     */
    override suspend fun getIntentSenderRequest(): IntentSenderRequest? {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, INTERVAL_MILLIS).build()

        val locationSettingsRequest =
            LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build()

        return runCatching {
            context.getSettingsClient().checkLocationSettings(locationSettingsRequest).await()
            null
        }.getOrElse { error ->
            if (error is ResolvableApiException) {
                IntentSenderRequest.Builder(error.resolution).build()
            } else {
                null
            }
        }
    }

    /**
     * Checks if the device has a GPS sensor.
     *
     * @return True if the device has a GPS sensor, false otherwise.
     */
    private fun Context.hasGpsSensor() = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)

    /**
     * Checks if GPS is enabled on the device.
     *
     * @return True if GPS is enabled, false otherwise.
     */
    private fun Context.isGpsEnabled(): Boolean {
        return runCatching {
            ContextCompat.getSystemService(
                this,
                LocationManager::class.java,
            )!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }.getOrElse {
            false
        }
    }

    /**
     * Extension function to get the [SettingsClient] from the [Context].
     *
     * @receiver The [Context] from which to get the [SettingsClient].
     * @return The [SettingsClient] instance.
     */
    private fun Context.getSettingsClient(): SettingsClient = LocationServices.getSettingsClient(this)

    private companion object {
        private const val INTERVAL_MILLIS = 1000L
    }
}

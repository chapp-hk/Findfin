package ch.app.hk.bank.locator.core.location.setting

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.result.IntentSenderRequest
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import kotlinx.coroutines.tasks.await

internal class SettingHelper(
    private val context: Context,
    private val settingsClient: SettingsClient,
) {
    fun getSettings(): LocationSettingStatus {
        return when {
            !context.hasGpsSensor() -> LocationSettingStatus.NoSensor
            !context.isGpsEnabled() -> LocationSettingStatus.Disabled
            else -> LocationSettingStatus.Enabled
        }
    }

    suspend fun getIntentSenderRequest(): IntentSenderRequest? {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, INTERVAL_MILLIS).build()

        val locationSettingsRequest =
            LocationSettingsRequest
                .Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)
                .build()

        return runCatching {
            settingsClient.checkLocationSettings(locationSettingsRequest).await()
            null
        }.getOrElse { error ->
            if (error is ResolvableApiException) {
                IntentSenderRequest.Builder(error.resolution).build()
            } else {
                null
            }
        }
    }

    private fun Context.hasGpsSensor(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
    }

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

    private companion object {
        private const val INTERVAL_MILLIS = 1000L
    }
}

package ch.app.hk.bank.locator.core.location.state.helper.setting

import androidx.activity.result.IntentSenderRequest
import ch.app.hk.bank.locator.core.location.state.LocationSettingResult
import ch.app.hk.bank.locator.core.location.state.helper.gps.GpsHelper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import kotlinx.coroutines.tasks.await

internal class SettingHelper(
    private val gpsHelper: GpsHelper,
    private val settingsClient: SettingsClient,
) {
    fun getSettings(): LocationSettingResult {
        return if (!gpsHelper.hasGpsSensor()) {
            LocationSettingResult.NoSensor
        } else if (gpsHelper.isGpsEnabled()) {
            LocationSettingResult.Enabled
        } else {
            LocationSettingResult.Disabled
        }
    }

    suspend fun getIntentSenderRequest(): IntentSenderRequest? {
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000L).build()

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
}

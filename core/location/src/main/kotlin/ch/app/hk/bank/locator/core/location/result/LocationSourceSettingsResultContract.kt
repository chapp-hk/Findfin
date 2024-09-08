package ch.app.hk.bank.locator.core.location.result

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ch.app.hk.bank.locator.core.location.helper.hardware.GpsHelper
import javax.inject.Inject

/**
 * An [ActivityResultContract] for navigating to the location source settings screen.
 *
 * This contract navigates the user to the location source settings screen
 * where they can enable or disable location services.
 * It then checks if the GPS is enabled on the device.
 *
 * @property gpsHelper A helper for checking if the GPS is enabled on the device.
 */
class LocationSourceSettingsResultContract @Inject constructor(
    private val gpsHelper: GpsHelper,
) : ActivityResultContract<Unit, Boolean>() {
    /**
     * Creates an [Intent] to navigate to the location source settings screen.
     *
     * @param context The context used to create the intent.
     * @param input The input parameter, which is not used in this implementation.
     * @return An [Intent] to navigate to the location source settings screen.
     */
    override fun createIntent(
        context: Context,
        input: Unit,
    ): Intent {
        return Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }

    /**
     * Parses the result of the activity and checks if the GPS is enabled on the device.
     *
     * @param resultCode The result code returned by the activity.
     * @param intent The intent returned by the activity.
     * @return `true` if the GPS is enabled, `false` otherwise.
     */
    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ): Boolean {
        return gpsHelper.isGpsEnabled()
    }
}

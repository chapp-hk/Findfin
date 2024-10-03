package ch.app.hk.bank.locator.core.location.setting.helper

import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.staticCompositionLocalOf
import ch.app.hk.bank.locator.core.location.setting.state.LocationSettingStatus

/**
 * A static composition local to provide a [SettingHelper] instance.
 * This is used to access location settings within a composable hierarchy.
 */
internal val LocalSettingHelper =
    staticCompositionLocalOf<SettingHelper> { error("SettingHelper is not provided") }

/**
 * Interface defining methods to interact with location settings.
 */
interface SettingHelper {
    /**
     * Retrieves the current location setting status.
     *
     * @return The current [LocationSettingStatus].
     * */
    fun getSettings(): LocationSettingStatus

    /**
     * Retrieves an [IntentSenderRequest] to prompt the user to enable location settings.
     *
     * @return An [IntentSenderRequest] if available, or null otherwise.
     * */
    suspend fun getIntentSenderRequest(): IntentSenderRequest?
}

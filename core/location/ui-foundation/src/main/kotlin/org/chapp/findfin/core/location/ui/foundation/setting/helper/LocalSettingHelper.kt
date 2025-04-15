package org.chapp.findfin.core.location.ui.foundation.setting.helper

import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.staticCompositionLocalOf
import org.chapp.findfin.core.location.ui.foundation.setting.state.LocationSettingStatus

/**
 * A static composition local to provide a [SettingHelper] instance.
 * This is used to access location settings within a composable hierarchy.
 */
val LocalSettingHelper =
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

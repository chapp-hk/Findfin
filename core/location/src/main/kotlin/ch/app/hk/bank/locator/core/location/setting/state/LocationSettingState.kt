package ch.app.hk.bank.locator.core.location.setting.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import ch.app.hk.bank.locator.core.location.setting.helper.SettingHelper

/**
 * Interface representing the state of location settings.
 */
@Stable
interface LocationSettingState {
    /**
     * The current status of the location settings.
     */
    val status: LocationSettingStatus

    /**
     * Launches an intent to enable location settings.
     */
    fun launchEnableLocation()
}

/**
 * Sealed interface representing the different statuses of location settings.
 */
@Stable
sealed interface LocationSettingStatus {
    /**
     * Indicates that the location settings are enabled.
     */
    data object Enabled : LocationSettingStatus

    /**
     * Indicates that the location settings are disabled.
     */
    data object Disabled : LocationSettingStatus

    /**
     * Indicates that the device does not have a GPS sensor.
     */
    data object NoSensor : LocationSettingStatus
}

/**
 * Remembers the state of location settings and provides a [LocationSettingState] instance.
 *
 * @param onResult A callback invoked with the current [LocationSettingState].
 * @return A [LocationSettingState] instance.
 */
@Composable
fun rememberLocationSettingState(
    settingHelper: SettingHelper,
    onResult: (LocationSettingState) -> Unit = {},
): LocationSettingState {
    return rememberMutableLocationSettingState(
        settingHelper = settingHelper,
        onResult = onResult,
    )
}

package ch.app.hk.bank.locator.core.location.setting.state

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import ch.app.hk.bank.locator.core.location.setting.helper.SettingHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A mutable implementation of [LocationSettingState] that manages the state of location settings.
 *
 * @property coroutineScope The [CoroutineScope] used for launching coroutines.
 * @property settingHelper The [SettingHelper] used to interact with location settings.
 */
@Stable
internal class MutableLocationSettingState(
    private val coroutineScope: CoroutineScope,
    private val settingHelper: SettingHelper,
) : LocationSettingState {
    /**
     * The current status of the location settings.
     */
    override var status: LocationSettingStatus by mutableStateOf(getLocationSettingStateResult())

    internal var enableLocationLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    /**
     * Launches an intent to enable location settings.
     */
    override fun launchEnableLocation() {
        coroutineScope.launch {
            settingHelper.getIntentSenderRequest()?.let {
                enableLocationLauncher?.launch(it)
            }
        }
    }

    /**
     * Refreshes the current status of the location settings.
     */
    internal fun refreshLocationSettingState() {
        status = getLocationSettingStateResult()
    }

    private fun getLocationSettingStateResult(): LocationSettingStatus {
        return settingHelper.getSettings()
    }
}

/**
 * Remembers the state of location settings and provides a [MutableLocationSettingState] instance.
 *
 * @param settingHelper The [SettingHelper] used to interact with location settings.
 * @param onResult A callback invoked with the current [LocationSettingState].
 * @return A [MutableLocationSettingState] instance.
 */
@Composable
internal fun rememberMutableLocationSettingState(
    settingHelper: SettingHelper,
    onResult: (LocationSettingState) -> Unit,
): MutableLocationSettingState {
    val coroutineScope = rememberCoroutineScope()
    val locationSettingState =
        remember(settingHelper) {
            MutableLocationSettingState(
                coroutineScope = coroutineScope,
                settingHelper = settingHelper,
            )
        }

    LocationSettingStateLifecycleEffect(locationSettingState)

    val enableLocationLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            locationSettingState.refreshLocationSettingState()
            onResult(locationSettingState)
        }

    DisposableEffect(locationSettingState, enableLocationLauncher) {
        locationSettingState.enableLocationLauncher = enableLocationLauncher

        onDispose {
            locationSettingState.enableLocationLauncher = null
        }
    }

    return locationSettingState
}

package ch.app.hk.bank.locator.core.location.setting

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
    internal var enableLocationLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    /**
     * The current status of the location settings.
     */
    override var status: LocationSettingStatus by mutableStateOf(getLocationStateResult())

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
    internal fun refreshLocationState() {
        status = getLocationStateResult()
    }

    private fun getLocationStateResult(): LocationSettingStatus {
        return settingHelper.getSettings()
    }
}

/**
 * Remembers the state of location settings and provides a [MutableLocationSettingState] instance.
 *
 * @param settingHelper The [SettingHelper] used to interact with location settings.
 * @param onResult A callback invoked with the current [LocationSettingStatus].
 * @return A [MutableLocationSettingState] instance.
 */
@Composable
internal fun rememberMutableLocationState(
    settingHelper: SettingHelper,
    onResult: (LocationSettingStatus) -> Unit,
): MutableLocationSettingState {
    val coroutineScope = rememberCoroutineScope()
    val locationState =
        remember {
            MutableLocationSettingState(
                coroutineScope = coroutineScope,
                settingHelper = settingHelper,
            )
        }

    val enableLocationLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            locationState.refreshLocationState()
            onResult(locationState.status)
        }

    LocationSettingStateLifecycleEffect(locationState)

    DisposableEffect(locationState, enableLocationLauncher) {
        locationState.enableLocationLauncher = enableLocationLauncher

        onDispose {
            locationState.enableLocationLauncher = null
        }
    }

    return locationState
}

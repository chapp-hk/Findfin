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

@Stable
internal class MutableLocationState(
    private val coroutineScope: CoroutineScope,
    private val settingHelper: SettingHelper,
) : LocationState {
    internal var enableLocationLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    override var status: LocationSettingStatus by mutableStateOf(getLocationStateResult())

    override fun launchEnableLocation() {
        coroutineScope.launch {
            settingHelper.getIntentSenderRequest()?.let {
                enableLocationLauncher?.launch(it)
            }
        }
    }

    internal fun refreshLocationState() {
        status = getLocationStateResult()
    }

    private fun getLocationStateResult(): LocationSettingStatus {
        return settingHelper.getSettings()
    }
}

@Composable
internal fun rememberMutableLocationState(
    settingHelper: SettingHelper,
    onLocationStateResult: (LocationSettingStatus) -> Unit,
): MutableLocationState {
    val coroutineScope = rememberCoroutineScope()
    val locationState =
        remember {
            MutableLocationState(
                coroutineScope = coroutineScope,
                settingHelper = settingHelper,
            )
        }

    val enableLocationLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            locationState.refreshLocationState()
            onLocationStateResult(locationState.status)
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

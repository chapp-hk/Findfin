package ch.app.hk.bank.locator.core.location.state

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
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.state.helper.gps.GpsHelper
import ch.app.hk.bank.locator.core.location.state.helper.setting.SettingHelper
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
interface LocationSettingState {
    val result: LocationSettingResult

    fun launchEnableLocation()
}

@Composable
fun rememberLocationSettingState(onLocationSettingResult: (LocationSettingResult) -> Unit = {}): LocationSettingState {
    val context = LocalContext.current
    val gpsHelper = GpsHelper(context)
    val settingClient = LocationServices.getSettingsClient(context)
    val settingHelper = SettingHelper(gpsHelper, settingClient)

    return rememberMutableLocationSettingState(
        settingHelper = settingHelper,
        onLocationSettingResult = onLocationSettingResult,
    )
}

@Stable
internal class MutableLocationSettingState(
    private val coroutineScope: CoroutineScope,
    private val settingHelper: SettingHelper,
) : LocationSettingState {
    override var result: LocationSettingResult by mutableStateOf(LocationSettingResult.None)

    internal var launcher: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun launchEnableLocation() {
        coroutineScope.launch {
            settingHelper.getIntentSenderRequest()?.let {
                launcher?.launch(it)
            }
        }
    }

    internal fun refreshLocationSettingState() {
        result = getLocationSettingResult()
    }

    private fun getLocationSettingResult(): LocationSettingResult {
        return settingHelper.getSettings()
    }
}

@Composable
internal fun rememberMutableLocationSettingState(
    settingHelper: SettingHelper,
    onLocationSettingResult: (LocationSettingResult) -> Unit,
): MutableLocationSettingState {
    val coroutineScope = rememberCoroutineScope()
    val locationSettingState =
        remember {
            MutableLocationSettingState(
                coroutineScope = coroutineScope,
                settingHelper = settingHelper,
            )
        }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            locationSettingState.refreshLocationSettingState()
            onLocationSettingResult(locationSettingState.result)
        }

    locationSettingState.launcher = launcher

    LocationSettingLifecycleCheckerEffect(locationSettingState)

    DisposableEffect(locationSettingState, launcher) {
        locationSettingState.launcher = launcher
        onDispose {
            locationSettingState.launcher = null
        }
    }

    return locationSettingState
}

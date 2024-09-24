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
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.helper.hardware.GpsHelperImpl
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
    val gpsHelperImpl = GpsHelperImpl(context)
    val settingClient = LocationServices.getSettingsClient(context)
    val locationSettingHelper = LocationSettingHelper(gpsHelperImpl, settingClient)

    return rememberMutableLocationSettingState(
        locationSettingHelper = locationSettingHelper,
        onLocationSettingResult = onLocationSettingResult,
    )
}

@Stable
internal class MutableLocationSettingState(
    private val coroutineScope: CoroutineScope,
    private val locationSettingHelper: LocationSettingHelper,
) : LocationSettingState {
    override var result: LocationSettingResult by mutableStateOf(LocationSettingResult.None)

    internal var launcher: ActivityResultLauncher<IntentSenderRequest>? = null

    override fun launchEnableLocation() {
        coroutineScope.launch {
            locationSettingHelper.getIntentSenderRequest()?.let {
                launcher?.launch(it)
            }
        }
    }

    internal fun refreshLocationSettingState() {
        result = getLocationSettingResult()
    }

    private fun getLocationSettingResult(): LocationSettingResult {
        return locationSettingHelper.getSettings()
    }
}

@Composable
internal fun rememberMutableLocationSettingState(
    locationSettingHelper: LocationSettingHelper,
    onLocationSettingResult: (LocationSettingResult) -> Unit,
): MutableLocationSettingState {
    val coroutineScope = rememberCoroutineScope()
    val locationSettingState =
        remember {
            MutableLocationSettingState(
                coroutineScope = coroutineScope,
                locationSettingHelper = locationSettingHelper,
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

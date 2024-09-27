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
import ch.app.hk.bank.locator.core.location.state.helper.permission.PermissionHelper
import ch.app.hk.bank.locator.core.location.state.helper.setting.SettingHelper
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
interface LocationState {
    val result: LocationStateResult

    fun launchEnableLocation()

    fun launchPermissionRequest()
}

@Composable
fun rememberLocationState(onLocationStateResult: (LocationStateResult) -> Unit = {}): LocationState {
    val context = LocalContext.current
    val settingHelper =
        SettingHelper(
            gpsHelper = GpsHelper(context),
            permissionHelper = PermissionHelper(context),
            settingsClient = LocationServices.getSettingsClient(context),
        )

    return rememberMutableLocationState(
        settingHelper = settingHelper,
        onLocationStateResult = onLocationStateResult,
    )
}

@Stable
internal class MutableLocationState(
    private val coroutineScope: CoroutineScope,
    private val settingHelper: SettingHelper,
) : LocationState {
    internal var enableLocationLauncher: ActivityResultLauncher<IntentSenderRequest>? = null
    internal var requestPermissionLauncher: ActivityResultLauncher<String>? = null

    override var result: LocationStateResult by mutableStateOf(getLocationStateResult())

    override fun launchEnableLocation() {
        coroutineScope.launch {
            settingHelper.getIntentSenderRequest()?.let {
                enableLocationLauncher?.launch(it)
            }
        }
    }

    override fun launchPermissionRequest() {
        requestPermissionLauncher?.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    internal fun refreshLocationState() {
        result = getLocationStateResult()
    }

    private fun getLocationStateResult(): LocationStateResult {
        return settingHelper.getSettings()
    }
}

@Composable
internal fun rememberMutableLocationState(
    settingHelper: SettingHelper,
    onLocationStateResult: (LocationStateResult) -> Unit,
): MutableLocationState {
    val coroutineScope = rememberCoroutineScope()
    val locationState =
        remember {
            MutableLocationState(
                coroutineScope = coroutineScope,
                settingHelper = settingHelper,
            )
        }

    // Remember RequestPermission launcher and assign it to permissionState
    val enableLocationLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            locationState.refreshLocationState()
            onLocationStateResult(locationState.result)
        }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            locationState.refreshLocationState()
            onLocationStateResult(locationState.result)
        }

    LocationStateLifecycleEffect(locationState)

    DisposableEffect(locationState, enableLocationLauncher, requestPermissionLauncher) {
        locationState.enableLocationLauncher = enableLocationLauncher
        locationState.requestPermissionLauncher = requestPermissionLauncher

        onDispose {
            locationState.enableLocationLauncher = null
            locationState.requestPermissionLauncher = null
        }
    }

    return locationState
}

package ch.app.hk.bank.locator.core.location.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.LocationServices

@Stable
interface LocationState {
    val status: LocationSettingStatus

    fun launchEnableLocation()
}

@Stable
sealed interface LocationSettingStatus {
    data object Enabled : LocationSettingStatus

    data object Disabled : LocationSettingStatus

    data object NoSensor : LocationSettingStatus
}

@Composable
fun rememberLocationSettingState(onLocationStateResult: (LocationSettingStatus) -> Unit = {}): LocationState {
    val context = LocalContext.current
    val settingHelper =
        SettingHelper(
            context = context,
            settingsClient = LocationServices.getSettingsClient(context),
        )

    return rememberMutableLocationState(
        settingHelper = settingHelper,
        onLocationStateResult = onLocationStateResult,
    )
}

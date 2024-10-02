package ch.app.hk.bank.locator.core.location.state

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ch.app.hk.bank.locator.core.location.state.setting.LocationSettingStatus
import ch.app.hk.bank.locator.core.location.state.setting.rememberLauncherForAppSetting
import ch.app.hk.bank.locator.core.location.state.setting.rememberLocationSettingState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@Stable
interface LocationState {
    val status: LocationSettingStatus
}

@Stable
sealed interface LocationStatus {
    data object TurnedOn : LocationStatus

    data object TurnedOff : LocationStatus

    data object NoSensor : LocationStatus

    data object PermissionGranted : LocationStatus

    data object PermissionDeniedPermanently : LocationStatus

    data object PermissionDeniedShowRationale : LocationStatus
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberLocationState() {
    var isUserDeniedPermission by remember { mutableStateOf(false) }

    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (isGranted) {
                // nearByViewModel.getNearByServices()
            } else {
                isUserDeniedPermission = true
            }
        }

    val locationState =
        rememberLocationSettingState { state ->
            if (state == LocationSettingStatus.Enabled) {
                locationPermissionState.launchPermissionRequest()
            }
        }

    val appSettingLauncher =
        rememberLauncherForAppSetting {
            isUserDeniedPermission = false
            locationPermissionState.launchPermissionRequest()
        }

    when (locationState.status) {
        LocationSettingStatus.NoSensor -> {
        }
        LocationSettingStatus.Disabled -> {
        }
        LocationSettingStatus.Enabled -> {
            when (val permission = locationPermissionState.status) {
                is PermissionStatus.Denied -> {
                    val isPermanentlyDenied = isUserDeniedPermission && !permission.shouldShowRationale
                }
                PermissionStatus.Granted -> {
                }
            }
        }
    }
}

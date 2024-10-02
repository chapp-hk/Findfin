package ch.app.hk.bank.locator.feature.home.ui.container.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.location.launcher.rememberLauncherForAppSetting
import ch.app.hk.bank.locator.core.location.setting.LocationSettingStatus
import ch.app.hk.bank.locator.core.location.setting.rememberLocationSettingState
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModelImpl
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeContainerContent(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    onSearch: (String) -> Unit,
) {
    val uiState by nearByViewModel.uiState.collectAsStateWithLifecycle()
    var isUserDeniedPermission by remember { mutableStateOf(false) }

    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (isGranted) {
                nearByViewModel.getNearByServices()
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

    val homeItem =
        when (val state = uiState) {
            is NearByUiState.Service -> HomeItem.Services(state.list)
            NearByUiState.Empty -> HomeItem.Empty
            else -> {
                when (locationState.status) {
                    LocationSettingStatus.NoSensor -> {
                        HomeItem.NoGps
                    }
                    LocationSettingStatus.Disabled -> {
                        HomeItem.LocationDisabled
                    }
                    LocationSettingStatus.Enabled -> {
                        when (val permission = locationPermissionState.status) {
                            is PermissionStatus.Denied -> {
                                val isPermanentlyDenied = isUserDeniedPermission && !permission.shouldShowRationale
                                HomeItem.LocationPermissionDenied(isPermanentlyDenied = isPermanentlyDenied)
                            }
                            PermissionStatus.Granted -> {
                                HomeItem.NearByLoading
                            }
                        }
                    }
                }
            }
        }

    if (locationPermissionState.status == PermissionStatus.Granted) {
        nearByViewModel.getNearByServices()
    }

    HomeContainerList(
        item = homeItem,
        onSearch = onSearch,
        onRequestEnableLocation = {
            locationState.launchEnableLocation()
        },
        onRequestLocationPermission = {
            if (isUserDeniedPermission && !locationPermissionState.status.shouldShowRationale) {
                appSettingLauncher.launch(Unit)
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        },
    )
}

package ch.app.hk.bank.locator.feature.home.ui.container.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.location.state.LocationStateResult
import ch.app.hk.bank.locator.core.location.state.helper.setting.rememberLauncherForAppSetting
import ch.app.hk.bank.locator.core.location.state.rememberLocationState
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModelImpl
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeContainerContent(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    onSearch: (String) -> Unit,
) {
    val uiState by nearByViewModel.uiState.collectAsStateWithLifecycle()
    var permissionRequestStartTime by remember { mutableLongStateOf(0L) }

    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (isGranted) {
                nearByViewModel.getNearByServices()
            } else {
                if (System.currentTimeMillis() - permissionRequestStartTime > 200L) {
                }
            }
            permissionRequestStartTime = 0L
        }

    val locationState =
        rememberLocationState { state ->
            if (state == LocationStateResult.Enabled) {
                locationPermissionState.launchPermissionRequest()
            }
        }

    val appSettingLauncher =
        rememberLauncherForAppSetting {
            permissionRequestStartTime = System.currentTimeMillis()
            locationPermissionState.launchPermissionRequest()
        }

    val homeItem =
        when (val state = uiState) {
            is NearByUiState.Service -> HomeItem.Services(state.list)
            NearByUiState.Empty -> HomeItem.Empty
            else -> {
                when (locationState.result) {
                    LocationStateResult.NoSensor -> {
                        HomeItem.NoGps
                    }
                    LocationStateResult.Disabled -> {
                        HomeItem.LocationDisabled
                    }
                    LocationStateResult.Enabled -> {
                        when (val permission = locationPermissionState.status) {
                            is PermissionStatus.Denied -> {
                                HomeItem.LocationPermissionDenied(isPermanentlyDenied = false)
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
//            if (System.currentTimeMillis() - permissionRequestStartTime > 200L) {
//                appSettingLauncher.launch(Unit)
//            } else {
//                permissionRequestStartTime = System.currentTimeMillis()
//                locationPermissionState.launchPermissionRequest()
//            }
            permissionRequestStartTime = System.currentTimeMillis()
            locationPermissionState.launchPermissionRequest()
        },
    )
}

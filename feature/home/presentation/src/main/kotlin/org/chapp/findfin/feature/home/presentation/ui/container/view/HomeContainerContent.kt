package org.chapp.findfin.feature.home.presentation.ui.container.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.chapp.findfin.core.location.ui.foundation.LocationSettingCheckContent
import org.chapp.findfin.core.location.ui.foundation.launcher.rememberLauncherForAppSetting
import org.chapp.findfin.core.location.ui.foundation.setting.state.LocationSettingStatus
import org.chapp.findfin.feature.home.presentation.navigation.HomeEvent
import org.chapp.findfin.feature.home.presentation.ui.container.model.HomeItem
import org.chapp.findfin.feature.home.presentation.ui.nearby.model.NearByUiState
import org.chapp.findfin.feature.home.presentation.ui.nearby.viewmodel.NearByViewModel
import org.chapp.findfin.feature.home.presentation.ui.nearby.viewmodel.NearByViewModelImpl

// TODO - fix detekt CognitiveComplexMethod(threshold: 15) and CyclomaticComplexMethod(threshold: 15)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun HomeContainerContent(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    homeEvent: HomeEvent,
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

    val appSettingLauncher =
        rememberLauncherForAppSetting {
            isUserDeniedPermission = false
            locationPermissionState.launchPermissionRequest()
        }

    LocationSettingCheckContent(
        onResult = { state ->
            if (state.status == LocationSettingStatus.Enabled) {
                locationPermissionState.launchPermissionRequest()
            }
        },
    ) { locationSettingState ->
        val homeItem =
            when (val state = uiState) {
                is NearByUiState.Service -> HomeItem.Services(state.list)
                NearByUiState.Empty -> HomeItem.Empty
                else -> {
                    when (locationSettingState.status) {
                        LocationSettingStatus.NoSensor -> HomeItem.NoGps
                        LocationSettingStatus.Disabled -> HomeItem.LocationDisabled
                        LocationSettingStatus.Enabled -> {
                            when (val permission = locationPermissionState.status) {
                                is PermissionStatus.Denied -> {
                                    val isPermanentlyDenied = isUserDeniedPermission && !permission.shouldShowRationale
                                    HomeItem.LocationPermissionDenied(isPermanentlyDenied = isPermanentlyDenied)
                                }

                                PermissionStatus.Granted -> HomeItem.NearByLoading
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
            homeEvent = homeEvent,
            onRequestEnableLocation = {
                locationSettingState.launchEnableLocation()
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
}

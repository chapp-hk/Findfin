package ch.app.hk.bank.locator.feature.home.ui.container.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.location.state.LocationSettingResult
import ch.app.hk.bank.locator.core.location.state.LocationSettingState
import ch.app.hk.bank.locator.core.location.state.rememberLocationSettingState
import ch.app.hk.bank.locator.core.permission.PermissionResult
import ch.app.hk.bank.locator.core.permission.rememberPermissionState
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModelImpl

@Composable
internal fun HomeContainerContent(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    locationSettingState: LocationSettingState = rememberLocationSettingState(),
    onSearch: (String) -> Unit,
) {
    var isUserRejectedPermission by remember { mutableStateOf(false) }
    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (!isGranted) {
                isUserRejectedPermission = true
            }
        }

    val uiState = nearByViewModel.uiState.collectAsStateWithLifecycle().value
    val locationSettings = locationSettingState.result
    val locationPermission = locationPermissionState.result

    val homeItem =
        when {
            uiState is NearByUiState.Service -> HomeItem.Services(uiState.list)

            uiState is NearByUiState.Empty -> HomeItem.Empty

            locationSettings == LocationSettingResult.Disabled -> HomeItem.LocationDisabled

            locationSettings == LocationSettingResult.NoSensor -> HomeItem.NoGps

            locationSettings == LocationSettingResult.Enabled &&
                locationPermission is PermissionResult.Denied ->
                HomeItem.LocationPermissionDenied(isPermanentlyDenied = isUserRejectedPermission)

            locationPermission is PermissionResult.Granted -> {
                nearByViewModel.getNearByServices()
                HomeItem.NearByLoading
            }

            else -> HomeItem.NearByLoading
        }

    HomeContainerList(
        item = homeItem,
        onSearch = onSearch,
        onRequestEnableLocation = {
            locationSettingState.launchEnableLocation()
        },
        onRequestLocationPermission = {
            if (isUserRejectedPermission) {
                locationPermissionState.launchAppSetting()
            } else {
                locationPermissionState.launchPermissionRequest()
            }
        },
    )
}

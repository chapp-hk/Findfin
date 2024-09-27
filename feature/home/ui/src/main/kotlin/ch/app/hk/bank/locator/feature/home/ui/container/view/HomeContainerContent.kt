package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.location.state.LocationState
import ch.app.hk.bank.locator.core.location.state.LocationStateResult
import ch.app.hk.bank.locator.core.location.state.rememberLocationState
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModelImpl

@Composable
internal fun HomeContainerContent(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    locationState: LocationState = rememberLocationState(),
    onSearch: (String) -> Unit,
) {
    val uiState = nearByViewModel.uiState.collectAsStateWithLifecycle().value
    val locationStateResult by remember { derivedStateOf { locationState.result } }

    val homeItem =
        remember(uiState, locationStateResult) {
            when {
                uiState is NearByUiState.Service ->
                    HomeItem.Services(uiState.list)

                uiState is NearByUiState.Empty ->
                    HomeItem.Empty

                locationStateResult == LocationStateResult.Disabled ->
                    HomeItem.LocationDisabled

                locationStateResult == LocationStateResult.NoSensor ->
                    HomeItem.NoGps

                locationStateResult is LocationStateResult.PermissionDenied ->
                    HomeItem.LocationPermissionDenied(isPermanentlyDenied = false)

                locationStateResult is LocationStateResult.Enabled ->
                    HomeItem.NearByLoading

                else ->
                    HomeItem.NearByLoading
            }
        }

    if (locationStateResult is LocationStateResult.Enabled) {
        nearByViewModel.getNearByServices()
    }

    HomeContainerList(
        item = homeItem,
        onSearch = onSearch,
        onRequestEnableLocation = {
            locationState.launchEnableLocation()
        },
        onRequestLocationPermission = {
//            if (isUserRejectedPermission) {
//                locationPermissionState.launchAppSetting()
//            } else {
//                locationPermissionState.launchPermissionRequest()
//            }
            locationState.launchPermissionRequest()
        },
    )
}

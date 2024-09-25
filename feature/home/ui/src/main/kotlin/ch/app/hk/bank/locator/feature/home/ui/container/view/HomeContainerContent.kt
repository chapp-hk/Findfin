package ch.app.hk.bank.locator.feature.home.ui.container.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.location.state.LocationSettingResult
import ch.app.hk.bank.locator.core.location.state.rememberLocationSettingState
import ch.app.hk.bank.locator.core.permission.PermissionResult
import ch.app.hk.bank.locator.core.permission.rememberPermissionState
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModelImpl

@Composable
internal fun HomeContainerContent(onSearch: (String) -> Unit) {
    val nearByServiceHeaderText = stringResource(id = R.string.home_title_nearby_services)
    val fixedItemList =
        remember {
            listOf(
                HomeItem.Search,
                HomeItem.Finding,
                HomeItem.StickyHeader(nearByServiceHeaderText),
            )
        }

    HomeContentLocationSettingCheck(
        fixedHomeItemList = fixedItemList,
        onSearch = onSearch,
    )
}

@Composable
private fun HomeContentLocationSettingCheck(
    fixedHomeItemList: List<HomeItem>,
    onSearch: (String) -> Unit,
) {
    val locationSettingState = rememberLocationSettingState()
    val homeItem =
        when (locationSettingState.result) {
            LocationSettingResult.None -> HomeItem.NearByLoading
            LocationSettingResult.NoSensor -> HomeItem.NoGps
            LocationSettingResult.Disabled -> HomeItem.LocationDisabled
            LocationSettingResult.Enabled -> HomeItem.Empty
        }

    if (homeItem == HomeItem.Empty) {
        HomeContentLocationPermissionCheck(
            fixedHomeItemList = fixedHomeItemList,
            onSearch = onSearch,
        )
    } else {
        HomeContainerList(
            items = fixedHomeItemList + homeItem,
            onSearch = onSearch,
            onRequestEnableLocation = {
                locationSettingState.launchEnableLocation()
            },
            onRequestPermission = {},
        )
    }
}

@Composable
private fun HomeContentLocationPermissionCheck(
    fixedHomeItemList: List<HomeItem>,
    onSearch: (String) -> Unit,
) {
    var isUserRejectedPermission by remember { mutableStateOf(false) }
    val locationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION) { isGranted ->
            if (!isGranted) {
                isUserRejectedPermission = true
            }
        }

    val homeItem =
        when (locationPermissionState.result) {
            is PermissionResult.Granted -> HomeItem.NearByLoading
            is PermissionResult.Denied ->
                HomeItem.LocationPermissionDenied(isPermanentlyDenied = isUserRejectedPermission)
        }

    if (homeItem == HomeItem.NearByLoading) {
        HomeContentNearByServices(
            fixedHomeItemList = fixedHomeItemList,
            onSearch = onSearch,
        )
    } else {
        HomeContainerList(
            items = fixedHomeItemList + homeItem,
            onSearch = onSearch,
            onRequestEnableLocation = {},
            onRequestPermission = {
                if (isUserRejectedPermission) {
                    locationPermissionState.launchAppSetting()
                } else {
                    locationPermissionState.launchPermissionRequest()
                }
            },
        )
    }
}

@Composable
private fun HomeContentNearByServices(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    fixedHomeItemList: List<HomeItem>,
    onSearch: (String) -> Unit,
) {
    val uiState = nearByViewModel.uiState.collectAsStateWithLifecycle()

    val homeItemList =
        fixedHomeItemList +
            when (val uiStateValue = uiState.value) {
                NearByUiState.Empty -> HomeItem.Empty
                NearByUiState.Error -> HomeItem.NearByLoading
                NearByUiState.Loading -> HomeItem.NearByLoading
                is NearByUiState.Service -> HomeItem.Services(uiStateValue.list)
            }

    HomeContainerList(
        items = homeItemList,
        onSearch = onSearch,
        onRequestEnableLocation = {},
        onRequestPermission = {},
    )
}

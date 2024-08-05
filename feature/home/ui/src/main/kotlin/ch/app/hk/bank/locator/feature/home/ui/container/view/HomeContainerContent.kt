package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByError
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModelImpl

@Composable
internal fun HomeContainerContent(
    nearByViewModel: NearByViewModel = hiltViewModel<NearByViewModelImpl>(),
    onSearch: (String) -> Unit,
) {
    val itemList =
        listOf(
            HomeItem.Search,
            HomeItem.Finding,
            HomeItem.StickyHeader(stringResource(id = R.string.home_title_nearby_services)),
        )

    ScreenStateView(
        state = nearByViewModel.uiState.collectAsStateWithLifecycle(),
        loading = {
            HomeContainerList(
                items = itemList + HomeItem.NearByLoading,
                onSearch = onSearch,
                onLocationEnabled = {
                },
            )
        },
        error = { error ->
            when (error.reason) {
                NearByError.PERMISSION_NOT_GRANTED -> {
                    HomeContainerList(
                        items = itemList + HomeItem.LocationPermissionDenied,
                        onSearch = onSearch,
                        onLocationEnabled = {
                            nearByViewModel.getNearByServices()
                        },
                    )
                }
                NearByError.GPS_NOT_SUPPORTED -> {
                    HomeContainerList(
                        items = itemList + HomeItem.NoGps,
                        onSearch = onSearch,
                        onLocationEnabled = {
                            nearByViewModel.getNearByServices()
                        },
                    )
                }
                NearByError.GPS_IS_OFF ->
                    HomeContainerList(
                        items = itemList + HomeItem.LocationDisabled,
                        onSearch = onSearch,
                        onLocationEnabled = {
                            nearByViewModel.getNearByServices()
                        },
                    )
                NearByError.UNKNOWN_ERROR -> {
                    // TODO - implementation here
                }
            }
        },
        empty = {
            HomeContainerList(
                items = itemList + HomeItem.Empty,
                onSearch = onSearch,
                onLocationEnabled = {
                    nearByViewModel.getNearByServices()
                },
            )
        },
        success = { data ->
            HomeContainerList(
                items = itemList + HomeItem.Services(data.list),
                onSearch = onSearch,
                onLocationEnabled = {
                    nearByViewModel.getNearByServices()
                },
            )
        },
    )
}

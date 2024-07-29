package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.core.design.ui.search.AppSearchBar
import ch.app.hk.bank.locator.core.design.ui.search.rememberAppSearchBarState
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.finding.view.Finding
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByError
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.LocationDisabledResult
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.LocationPermissionDeniedResult
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
                    // TODO - implementation
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
                    // TODO - implementation
                }
            }
        },
        empty = {
        },
        success = { data ->
        },
    )
}

@Composable
private fun HomeContainerList(
    items: List<HomeItem>,
    onSearch: (String) -> Unit,
    onLocationEnabled: () -> Unit,
) {
    LazyColumn {
        itemsIndexed(
            items = items,
            key = { index, _ -> index },
        ) { _, item ->
            when (item) {
                HomeItem.Search -> {
                    val searchBarState =
                        rememberAppSearchBarState(
                            placeholder = stringResource(id = R.string.home_placeholder_search),
                        )

                    val description =
                        stringResource(id = R.string.home_content_description_search)

                    AppSearchBar(
                        modifier = Modifier.semantics { contentDescription = description },
                        state = searchBarState,
                        onSearch = onSearch,
                    )
                }

                HomeItem.Finding -> {
                    val description =
                        stringResource(id = R.string.home_content_description_finding)

                    Finding(
                        modifier = Modifier.semantics { contentDescription = description },
                        onFindYourBank = {},
                        onFindBankOrAtms = {},
                    )
                }

                is HomeItem.StickyHeader -> {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                        text = item.header,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                HomeItem.NearByLoading -> {
                    val loadingContentDescription = stringResource(id = R.string.home_content_description_loading)

                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .semantics { contentDescription = loadingContentDescription }
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center),
                    )
                }

                HomeItem.LocationDisabled -> {
                    val contentDescription = stringResource(id = R.string.home_content_description_location_disabled)

                    LocationDisabledResult(
                        modifier = Modifier.semantics { this.contentDescription = contentDescription },
                        onLocationServiceEnabled = onLocationEnabled,
                    )
                }

                HomeItem.LocationPermissionDenied -> {
                    val contentDescription = stringResource(id = R.string.home_content_description_location_permission_denied)

                    LocationPermissionDeniedResult(
                        modifier = Modifier.semantics { this.contentDescription = contentDescription },
                        onPermissionGranted = onLocationEnabled,
                    )
                }
            }
        }
    }
}

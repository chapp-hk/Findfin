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
import androidx.compose.ui.unit.dp
import ch.app.hk.bank.locator.core.design.ui.modifier.contentDescription
import ch.app.hk.bank.locator.core.design.ui.search.AppSearchBar
import ch.app.hk.bank.locator.core.design.ui.search.rememberAppSearchBarState
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.finding.view.Finding
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.DeviceNoGpsResult
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.EmptyResult
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.LocationDisabled
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.LocationPermissionDenied
import ch.app.hk.bank.locator.feature.home.ui.nearby.view.ServiceItem

@Composable
internal fun HomeContainerList(
    items: List<HomeItem>,
    onSearch: (String) -> Unit,
    onRequestLocation: () -> Unit,
) {
    LazyColumn {
        itemsIndexed(
            items = items,
            key = { _, item -> item.hashCode() },
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
                        modifier = Modifier.contentDescription(description),
                        state = searchBarState,
                        onSearch = onSearch,
                    )
                }

                HomeItem.Finding -> {
                    val description =
                        stringResource(id = R.string.home_content_description_finding)

                    Finding(
                        modifier = Modifier.contentDescription(description),
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
                    val loadingContentDescription =
                        stringResource(id = R.string.home_content_description_loading)

                    CircularProgressIndicator(
                        modifier =
                            Modifier
                                .contentDescription(loadingContentDescription)
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center),
                    )
                }

                HomeItem.NoGps -> DeviceNoGpsResult()

                HomeItem.LocationDisabled -> {
                    val contentDescription =
                        stringResource(id = R.string.home_content_description_location_disabled)

                    LocationDisabled(
                        modifier = Modifier.contentDescription(contentDescription),
                        onRequestEnableLocation = onRequestLocation,
                    )
                }

                is HomeItem.LocationPermissionDenied -> {
                    val contentDescription =
                        stringResource(id = R.string.home_content_description_location_permission_denied)

                    LocationPermissionDenied(
                        modifier = Modifier.contentDescription(contentDescription),
                        isPermanentlyDenied = item.isPermanentlyDenied,
                        onRequestPermission = onRequestLocation,
                    )
                }

                HomeItem.Empty -> {
                    EmptyResult()
                }

                is HomeItem.Services -> {
                    item.list.forEach { service ->
                        ServiceItem(service = service)
                    }
                }
            }
        }
    }
}

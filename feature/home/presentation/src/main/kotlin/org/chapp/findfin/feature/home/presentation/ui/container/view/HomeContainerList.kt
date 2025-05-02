package org.chapp.findfin.feature.home.presentation.ui.container.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.chapp.findfin.core.design.ui.foundation.modifier.contentDescription
import org.chapp.findfin.core.design.ui.foundation.search.AppSearchBar
import org.chapp.findfin.core.design.ui.foundation.search.rememberAppSearchBarState
import org.chapp.findfin.feature.home.presentation.R
import org.chapp.findfin.feature.home.presentation.navigation.HomeEvent
import org.chapp.findfin.feature.home.presentation.ui.container.model.HomeItem
import org.chapp.findfin.feature.home.presentation.ui.finding.view.Finding
import org.chapp.findfin.feature.home.presentation.ui.nearby.view.DeviceNoGpsResult
import org.chapp.findfin.feature.home.presentation.ui.nearby.view.EmptyResult
import org.chapp.findfin.feature.home.presentation.ui.nearby.view.LocationDisabled
import org.chapp.findfin.feature.home.presentation.ui.nearby.view.LocationPermissionDenied
import org.chapp.findfin.feature.home.presentation.ui.nearby.view.ServiceItem

@Composable
internal fun HomeContainerList(
    item: HomeItem,
    homeEvent: HomeEvent,
    onRequestEnableLocation: () -> Unit,
    onRequestLocationPermission: () -> Unit,
) {
    val headerItems = rememberHomeList()

    LazyColumn {
        itemsIndexed(
            items = headerItems + item,
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
                        onSearch = homeEvent.onSearch,
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
                        onRequestEnableLocation = onRequestEnableLocation,
                    )
                }

                is HomeItem.LocationPermissionDenied -> {
                    val contentDescription =
                        stringResource(id = R.string.home_content_description_location_permission_denied)

                    LocationPermissionDenied(
                        modifier = Modifier.contentDescription(contentDescription),
                        isPermanentlyDenied = item.isPermanentlyDenied,
                        onRequestPermission = onRequestLocationPermission,
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

@Composable
private fun rememberHomeList(): List<HomeItem> {
    val nearByServiceHeaderText = stringResource(id = R.string.home_title_nearby_services)
    return remember {
        listOf(
            HomeItem.Search,
            HomeItem.Finding,
            HomeItem.StickyHeader(nearByServiceHeaderText),
        )
    }
}

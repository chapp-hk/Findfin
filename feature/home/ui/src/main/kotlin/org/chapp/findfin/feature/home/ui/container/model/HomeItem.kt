package org.chapp.findfin.feature.home.ui.container.model

import org.chapp.findfin.feature.home.ui.nearby.model.NearByItemUiModel

sealed interface HomeItem {
    data object Search : HomeItem

    data object Finding : HomeItem

    data class StickyHeader(val header: String) : HomeItem

    data object NearByLoading : HomeItem

    data object NoGps : HomeItem

    data object LocationDisabled : HomeItem

    data class LocationPermissionDenied(val isPermanentlyDenied: Boolean) : HomeItem

    data object Empty : HomeItem

    data class Services(val list: List<NearByItemUiModel>) : HomeItem
}

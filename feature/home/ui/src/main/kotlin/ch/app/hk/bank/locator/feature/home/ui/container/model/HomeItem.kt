package ch.app.hk.bank.locator.feature.home.ui.container.model

sealed interface HomeItem {
    data object Search : HomeItem

    data object Finding : HomeItem

    data class StickyHeader(val header: String) : HomeItem

    data object NearByLoading : HomeItem

    data object LocationDisabled : HomeItem
}

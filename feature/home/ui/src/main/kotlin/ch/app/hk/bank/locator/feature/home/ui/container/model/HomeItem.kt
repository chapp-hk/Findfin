package ch.app.hk.bank.locator.feature.home.ui.container.model

sealed interface HomeItem {
    data object Search : HomeItem

    data object Finding : HomeItem
}

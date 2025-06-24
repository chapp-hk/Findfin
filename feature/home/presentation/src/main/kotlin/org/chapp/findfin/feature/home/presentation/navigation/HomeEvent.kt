package org.chapp.findfin.feature.home.presentation.navigation

import org.chapp.findfin.feature.locator.presentation.navigation.MapSearchType

data class HomeEvent(
    val onRequestAuth: () -> Unit,
    val onSearch: (String) -> Unit,
    val navigateToMap: (MapSearchType) -> Unit,
)

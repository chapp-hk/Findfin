package org.chapp.findfin.feature.home.presentation.navigation

data class HomeEvent(
    val onRequestAuth: () -> Unit,
    val onSearch: (String) -> Unit,
)

package org.chapp.findfin.feature.home.presentation.ui.nearby.model

sealed interface NearByUiState {
    data object Loading : NearByUiState

    data object Error : NearByUiState

    data object Empty : NearByUiState

    data class Service(val list: List<NearByItemUiModel>) : NearByUiState
}

package ch.app.hk.bank.locator.feature.home.ui.nearby.model

sealed interface NearByUiState {
    data object Loading : NearByUiState

    data object Error : NearByUiState

    data object Empty : NearByUiState

    data class Service(val list: List<NearByItemUiModel>) : NearByUiState
}

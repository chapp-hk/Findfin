package ch.app.hk.bank.locator.feature.home.ui.nearby.model

sealed interface NearByUiState {
    data class Error(val reason: NearByError) : NearByUiState

    data class Service(val list: List<NearByItemUiModel>) : NearByUiState
}

enum class NearByError {
    PERMISSION_NOT_GRANTED,
    GPS_NOT_SUPPORTED,
    GPS_IS_OFF,
    UNKNOWN_ERROR,
}

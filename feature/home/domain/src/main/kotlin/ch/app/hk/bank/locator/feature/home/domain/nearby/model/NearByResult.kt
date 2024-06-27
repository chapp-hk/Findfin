package ch.app.hk.bank.locator.feature.home.domain.nearby.model

sealed interface NearByResult {
    data object PermissionNotGranted : NearByResult

    data object GpsNotSupported : NearByResult

    data object GpsIsOff : NearByResult

    data object UnknownError : NearByResult

    data class Location(val list: List<Service>) : NearByResult
}

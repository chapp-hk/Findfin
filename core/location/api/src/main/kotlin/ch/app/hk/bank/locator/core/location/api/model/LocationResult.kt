package ch.app.hk.bank.locator.core.location.api.model

sealed interface LocationResult {
    data class Location(val lat: Double, val lon: Double) : LocationResult

    data object PermissionNotGranted : LocationResult

    data object GpsNotSupported : LocationResult

    data object GpsIsOff : LocationResult

    data object UnknownError : LocationResult
}

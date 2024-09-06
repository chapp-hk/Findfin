package ch.app.hk.bank.locator.feature.locator.data.repo.model

sealed interface LocationResult {
    data class Location(val lat: Double, val lon: Double) : LocationResult

    data object PermissionNotGranted : LocationResult

    data object GpsNotSupported : LocationResult

    data object GpsIsOff : LocationResult

    data object UnknownError : LocationResult
}

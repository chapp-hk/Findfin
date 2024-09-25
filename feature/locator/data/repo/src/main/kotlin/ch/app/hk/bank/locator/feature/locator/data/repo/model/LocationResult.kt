package ch.app.hk.bank.locator.feature.locator.data.repo.model

sealed interface LocationResult {
    data class Location(val lat: Double, val lon: Double) : LocationResult

    data object UnknownError : LocationResult
}

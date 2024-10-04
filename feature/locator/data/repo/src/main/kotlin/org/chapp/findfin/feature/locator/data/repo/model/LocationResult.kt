package org.chapp.findfin.feature.locator.data.repo.model

sealed interface LocationResult {
    data class Location(val lat: Double, val lon: Double) : LocationResult

    data object UnknownError : LocationResult
}

package ch.app.hk.bank.locator.core.location.api.help

import ch.app.hk.bank.locator.core.location.api.model.LocationResult

interface LocationHelper {
    suspend fun getSingleCurrentLocation(): LocationResult
}

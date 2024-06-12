package ch.app.hk.bank.locator.core.location.api.repo

import ch.app.hk.bank.locator.core.location.api.model.LocationResult

interface LocationRepository {
    suspend fun getSingleCurrentLocation(): LocationResult
}

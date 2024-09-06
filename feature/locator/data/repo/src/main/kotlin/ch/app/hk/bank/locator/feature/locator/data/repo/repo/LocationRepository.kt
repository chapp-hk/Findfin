package ch.app.hk.bank.locator.feature.locator.data.repo.repo

import ch.app.hk.bank.locator.feature.locator.data.repo.model.LocationResult

interface LocationRepository {
    suspend fun getSingleCurrentLocation(): LocationResult
}

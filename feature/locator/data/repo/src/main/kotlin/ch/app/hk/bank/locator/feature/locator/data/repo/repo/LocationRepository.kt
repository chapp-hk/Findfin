package org.chapp.findfin.feature.locator.data.repo.repo

import org.chapp.findfin.feature.locator.data.repo.model.LocationResult

interface LocationRepository {
    suspend fun getCurrentLocation(): LocationResult
}

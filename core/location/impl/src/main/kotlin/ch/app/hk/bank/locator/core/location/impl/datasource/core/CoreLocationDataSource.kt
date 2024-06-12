package ch.app.hk.bank.locator.core.location.impl.datasource.core

import android.location.Location

internal interface CoreLocationDataSource {
    suspend fun getSingleCurrentLocation(
        provider: CoreLocationProvider,
        timeoutMillis: Long,
    ): Location?
}

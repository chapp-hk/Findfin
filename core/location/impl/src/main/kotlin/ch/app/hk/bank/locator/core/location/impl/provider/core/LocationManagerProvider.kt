package ch.app.hk.bank.locator.core.location.impl.provider.core

import android.location.Location

internal interface LocationManagerProvider {
    suspend fun getSingleCurrentLocation(): Location?
}

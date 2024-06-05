package ch.app.hk.bank.locator.core.location.impl.provider.fused

import android.location.Location

interface FusedLocationProvider {
    suspend fun getSingleCurrentLocation(): Location?
}

package ch.app.hk.bank.locator.core.location.impl.datasource.fused

import android.location.Location

internal interface FusedLocationDataSource {
    suspend fun getSingleCurrentLocation(): Location?
}

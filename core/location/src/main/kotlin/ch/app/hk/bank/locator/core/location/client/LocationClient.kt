package ch.app.hk.bank.locator.core.location.client

import android.location.Location

interface LocationClient {
    suspend fun getSingleCurrentLocation(): Location?
}

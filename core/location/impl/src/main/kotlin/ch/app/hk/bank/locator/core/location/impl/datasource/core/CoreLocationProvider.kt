package ch.app.hk.bank.locator.core.location.impl.datasource.core

import android.location.LocationManager

enum class CoreLocationProvider(val value: String) {
    GPS(LocationManager.GPS_PROVIDER),
    NETWORK(LocationManager.NETWORK_PROVIDER),
}

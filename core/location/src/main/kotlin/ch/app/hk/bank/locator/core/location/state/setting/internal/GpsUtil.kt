package ch.app.hk.bank.locator.core.location.state.setting.internal

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat

internal fun Context.hasGpsSensor(): Boolean {
    return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
}

internal fun Context.isGpsEnabled(): Boolean {
    return runCatching {
        ContextCompat.getSystemService(
            this,
            LocationManager::class.java,
        )!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }.getOrElse {
        false
    }
}

package ch.app.hk.bank.locator.core.location.impl.util.hardware

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltExtBindModule
internal class GpsUtilImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : GpsUtil {
    override fun hasGpsSensor(): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)
    }

    override fun isGpsEnabled(): Boolean {
        return runCatching {
            ContextCompat.getSystemService(
                context,
                LocationManager::class.java,
            )!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }.getOrElse {
            false
        }
    }
}

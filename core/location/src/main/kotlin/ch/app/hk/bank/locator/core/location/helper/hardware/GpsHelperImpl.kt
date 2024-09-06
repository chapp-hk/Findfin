package ch.app.hk.bank.locator.core.location.helper.hardware

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltWrapBindModule
internal class GpsHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : GpsHelper {
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

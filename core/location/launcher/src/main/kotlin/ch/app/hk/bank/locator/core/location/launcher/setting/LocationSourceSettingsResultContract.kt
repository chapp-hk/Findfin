package ch.app.hk.bank.locator.core.location.launcher.setting

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.ContextCompat

class LocationSourceSettingsResultContract(
    private val context: Context,
) : ActivityResultContract<Unit, Boolean>() {
    override fun createIntent(
        context: Context,
        input: Unit,
    ): Intent {
        return Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ): Boolean {
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

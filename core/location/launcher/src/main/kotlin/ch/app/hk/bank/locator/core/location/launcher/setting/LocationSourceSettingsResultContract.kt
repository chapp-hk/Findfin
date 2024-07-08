package ch.app.hk.bank.locator.core.location.launcher.setting

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import javax.inject.Inject

class LocationSourceSettingsResultContract @Inject constructor(
    private val gpsHelper: GpsHelper,
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
        return gpsHelper.isGpsEnabled()
    }
}

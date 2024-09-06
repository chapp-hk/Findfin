package ch.app.hk.bank.locator.core.location.result

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import ch.app.hk.bank.locator.core.location.helper.permission.PermissionHelper
import javax.inject.Inject

class LocationPermissionResultContract @Inject constructor(
    private val permissionHelper: PermissionHelper,
) : ActivityResultContract<Unit, Boolean>() {
    override fun createIntent(
        context: Context,
        input: Unit,
    ): Intent {
        return Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null),
        )
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ): Boolean {
        return permissionHelper.checkPermission()
    }
}

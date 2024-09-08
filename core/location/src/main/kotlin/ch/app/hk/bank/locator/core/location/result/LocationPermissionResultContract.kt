package ch.app.hk.bank.locator.core.location.result

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import ch.app.hk.bank.locator.core.location.helper.permission.PermissionHelper
import javax.inject.Inject

/**
 * An [ActivityResultContract] for checking location permissions.
 *
 * This contract navigates the user to the application settings screen where they can grant or deny location permissions.
 * It then checks if the necessary location permissions are granted.
 *
 * @property permissionHelper A helper for checking if the necessary permissions are granted.
 */
class LocationPermissionResultContract @Inject constructor(
    private val permissionHelper: PermissionHelper,
) : ActivityResultContract<Unit, Boolean>() {
    /**
     * Creates an [Intent] to navigate to the application settings screen.
     *
     * @param context The context used to create the intent.
     * @param input The input parameter, which is not used in this implementation.
     * @return An [Intent] to navigate to the application settings screen.
     */
    override fun createIntent(
        context: Context,
        input: Unit,
    ): Intent {
        return Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null),
        )
    }

    /**
     * Parses the result of the activity and checks if the necessary location permissions are granted.
     *
     * @param resultCode The result code returned by the activity.
     * @param intent The intent returned by the activity.
     * @return `true` if the necessary location permissions are granted, `false` otherwise.
     */
    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ): Boolean {
        return permissionHelper.checkPermission()
    }
}

package ch.app.hk.bank.locator.core.location.helper.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Implementation of the [PermissionHelper] interface that checks for location permissions.
 *
 * @property context The application context used to access system services.
 */
@HiltWrapBindModule
internal class PermissionHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PermissionHelper {
    /**
     * Checks if the necessary location permissions are granted.
     *
     * @return `true` if the `ACCESS_FINE_LOCATION` permission is granted, `false` otherwise.
     */
    override fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }
}

package ch.app.hk.bank.locator.core.location.impl.helper.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ch.app.library.hiltwrap.annotation.HiltExtBindModule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltExtBindModule
internal class PermissionHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : PermissionHelper {
    override fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }
}

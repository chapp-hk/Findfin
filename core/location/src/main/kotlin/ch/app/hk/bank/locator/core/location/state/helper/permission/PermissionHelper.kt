package ch.app.hk.bank.locator.core.location.state.helper.permission

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionHelper(private val context: Context) {
    fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
    }
}

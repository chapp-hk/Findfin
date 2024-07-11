package ch.app.hk.bank.locator.core.location.launcher.permission

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.impl.di.LocationHelperEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun rememberLocationPermissionLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<Unit, Boolean> {
    val locationHelperEntryPoint =
        EntryPointAccessors.fromApplication(
            context = LocalContext.current,
            entryPoint = LocationHelperEntryPoint::class.java,
        )

    return rememberLauncherForActivityResult(
        contract = LocationPermissionResultContract(locationHelperEntryPoint.getPermissionHelper()),
        onResult = onResult,
    )
}

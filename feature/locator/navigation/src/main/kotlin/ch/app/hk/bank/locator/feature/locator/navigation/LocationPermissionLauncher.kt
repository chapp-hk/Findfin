package ch.app.hk.bank.locator.feature.locator.navigation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.di.LocationHelperEntryPoint
import ch.app.hk.bank.locator.core.location.helper.permission.PermissionHelper
import ch.app.hk.bank.locator.core.location.result.LocationPermissionResultContract
import dagger.hilt.android.EntryPointAccessors

/**
 * Remembers a launcher for the location permission result contract.
 *
 * This composable function creates and remembers a launcher for the [LocationPermissionResultContract].
 * It uses the [LocationHelperEntryPoint] to get the [PermissionHelper] and checks if the necessary location permissions are granted.
 *
 * @param onResult A callback to be invoked with the result of the permission check.
 * @return A [ManagedActivityResultLauncher] that can be used to launch the location permission request.
 */
@Composable
fun rememberLocationPermissionLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<Unit, Boolean> {
    val locationHelperEntryPoint =
        EntryPointAccessors.fromApplication(
            context = LocalContext.current,
            entryPoint = LocationHelperEntryPoint::class.java,
        )

    return rememberLauncherForActivityResult(
        contract =
            LocationPermissionResultContract(
                locationHelperEntryPoint.getPermissionHelper(),
            ),
        onResult = onResult,
    )
}

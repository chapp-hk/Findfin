package ch.app.hk.bank.locator.feature.locator.navigation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.di.LocationHelperEntryPoint
import ch.app.hk.bank.locator.core.location.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.result.LocationSourceSettingsResultContract
import dagger.hilt.android.EntryPointAccessors

/**
 * Remembers a launcher for the location source settings result contract.
 *
 * This composable function creates and remembers a launcher for the [LocationSourceSettingsResultContract].
 * It uses the [LocationHelperEntryPoint] to get the [GpsHelper] and checks if the GPS is enabled on the device.
 *
 * @param onResult A callback to be invoked with the result of the GPS check.
 * @return A [ManagedActivityResultLauncher] that can be used to launch the location source settings request.
 */
@Composable
fun rememberLocationSourceSettingsLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<Unit, Boolean> {
    val locationHelperEntryPoint =
        EntryPointAccessors.fromApplication(
            context = LocalContext.current,
            entryPoint = LocationHelperEntryPoint::class.java,
        )

    return rememberLauncherForActivityResult(
        contract =
            LocationSourceSettingsResultContract(
                locationHelperEntryPoint.getGpsHelper(),
            ),
        onResult = onResult,
    )
}

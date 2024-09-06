package ch.app.hk.bank.locator.feature.locator.navigation

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.di.LocationHelperEntryPoint
import ch.app.hk.bank.locator.core.location.result.LocationSourceSettingsResultContract
import dagger.hilt.android.EntryPointAccessors

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

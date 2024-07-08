package ch.app.hk.bank.locator.core.location.launcher.setting

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberLocationSourceSettingsLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<Unit, Boolean> {
    val locationSourceSettingsViewModel = hiltViewModel<LocationSourceSettingsViewModel>()
    return rememberLauncherForActivityResult(
        contract = locationSourceSettingsViewModel.locationSourceSettingsResultContract,
        onResult = onResult,
    )
}

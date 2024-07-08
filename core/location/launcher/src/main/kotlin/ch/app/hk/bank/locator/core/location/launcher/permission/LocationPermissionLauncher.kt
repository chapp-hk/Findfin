package ch.app.hk.bank.locator.core.location.launcher.permission

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun rememberLocationPermissionLauncher(onResult: (Boolean) -> Unit): ManagedActivityResultLauncher<Unit, Boolean> {
    val locationPermissionViewModel = hiltViewModel<LocationPermissionViewModel>()
    return rememberLauncherForActivityResult(
        contract = locationPermissionViewModel.locationPermissionResultContract,
        onResult = onResult,
    )
}

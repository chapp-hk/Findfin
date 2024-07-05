package ch.app.hk.bank.locator.core.location.launcher.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberLocationPermissionLauncher(onResult: (Boolean) -> Unit) =
    rememberLauncherForActivityResult(
        contract = LocationPermissionResultContract(context = LocalContext.current),
        onResult = onResult,
    )

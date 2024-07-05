package ch.app.hk.bank.locator.core.location.launcher.setting

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberLocationSourceSettingsLauncher(onResult: (Boolean) -> Unit) =
    rememberLauncherForActivityResult(
        contract = LocationSourceSettingsResultContract(context = LocalContext.current),
        onResult = onResult,
    )

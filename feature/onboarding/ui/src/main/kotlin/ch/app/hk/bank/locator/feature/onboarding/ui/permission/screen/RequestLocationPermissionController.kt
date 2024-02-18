package ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun RequestLocationPermissionController(
    modifier: Modifier = Modifier,
    completeOnboarding: () -> Unit,
) {
    val isShowDialog = remember { mutableStateOf(false) }
    LocationPermissionNotGrantedDialog(
        isShowDialog = isShowDialog.value,
    ) {
        completeOnboarding()
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                completeOnboarding()
            } else {
                isShowDialog.value = true
            }
        }

    RequestLocationPermissionContent(
        modifier = modifier,
        onGrantPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
        onSkip = { isShowDialog.value = true },
    )
}

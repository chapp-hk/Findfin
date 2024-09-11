package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun RequestLocationPermissionScreen(onFinishRequestPermission: () -> Unit) {
    val isShowDialog = remember { mutableStateOf(false) }
    LocationPermissionNotGrantedDialog(
        isShowDialog = isShowDialog.value,
    ) {
        onFinishRequestPermission()
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            if (isGranted) {
                onFinishRequestPermission()
            } else {
                isShowDialog.value = true
            }
        }

    RequestLocationPermissionContent(
        modifier = Modifier,
        onGrantPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
        onSkip = { isShowDialog.value = true },
    )
}

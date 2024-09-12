package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ch.app.hk.bank.locator.core.permission.rememberPermissionState

@Composable
fun RequestLocationPermissionScreen(onFinishRequestPermission: () -> Unit) {
    val isShowDialog = remember { mutableStateOf(false) }
    LocationPermissionNotGrantedDialog(
        isShowDialog = isShowDialog.value,
    ) {
        onFinishRequestPermission()
    }

    val permissionState =
        rememberPermissionState(
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) { isGranted ->
            if (isGranted) {
                onFinishRequestPermission()
            } else {
                isShowDialog.value = true
            }
        }

    RequestLocationPermissionContent(
        modifier = Modifier,
        onGrantPermission = { permissionState.launchPermissionRequest() },
        onSkip = { isShowDialog.value = true },
    )
}

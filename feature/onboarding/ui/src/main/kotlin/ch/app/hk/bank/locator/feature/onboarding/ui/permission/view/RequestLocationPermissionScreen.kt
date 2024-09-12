package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ch.app.hk.bank.locator.core.permission.rememberPermissionState

@Composable
fun RequestLocationPermissionScreen(onFinishRequestPermission: () -> Unit) {
    val permissionState =
        rememberPermissionState(
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) {
            onFinishRequestPermission()
        }

    RequestLocationPermissionContent(
        modifier = Modifier,
        onGrantPermission = { permissionState.launchPermissionRequest() },
        onSkip = {
            onFinishRequestPermission()
        },
    )
}

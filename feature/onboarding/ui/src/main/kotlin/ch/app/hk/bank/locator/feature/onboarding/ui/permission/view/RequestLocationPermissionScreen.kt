package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel.PermissionViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel.PermissionViewModelImpl

@Composable
fun RequestLocationPermissionScreen(
    permissionViewModel: PermissionViewModel = hiltViewModel<PermissionViewModelImpl>(),
    finishOnboarding: () -> Unit,
) {
    ScreenStateView(
        state = permissionViewModel.uiState.collectAsStateWithLifecycle(),
        empty = {
            RequestLocationPermissionController(
                modifier = Modifier.testTag(tag = TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_SCREEN),
            ) {
                permissionViewModel.completeOnboarding()
            }
        },
        success = {
            finishOnboarding()
        },
    )
}

internal const val TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_SCREEN =
    "onboarding_request_location_permission_screen"

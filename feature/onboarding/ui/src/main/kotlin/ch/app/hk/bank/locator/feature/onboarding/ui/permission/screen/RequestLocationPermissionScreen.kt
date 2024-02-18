package ch.app.hk.bank.locator.feature.onboarding.ui.permission.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel.PermissionViewModel
import ch.app.hk.bank.locator.feature.onboarding.ui.permission.viewmodel.PermissionViewModelImpl

@Composable
fun RequestLocationPermissionScreen(
    permissionViewModel: PermissionViewModel = hiltViewModel<PermissionViewModelImpl>(),
    goToHome: () -> Unit,
) {
    ScreenStateView(
        state = permissionViewModel.uiState.collectAsStateWithLifecycle().value,
        empty = {
            RequestLocationPermissionContent(
                modifier = Modifier.testTag(tag = TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_SCREEN),
            ) {
                permissionViewModel.completeOnboarding()
            }
        },
        success = {
            goToHome()
        },
    )
}

internal const val TEST_TAG_ONBOARDING_REQUEST_LOCATION_PERMISSION_SCREEN =
    "onboarding_request_location_permission_screen"

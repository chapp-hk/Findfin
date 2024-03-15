package ch.app.hk.bank.locator.feature.onboarding.ui.permission.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
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
            RequestLocationPermissionController {
                permissionViewModel.completeOnboarding()
            }
        },
        success = {
            finishOnboarding()
        },
    )
}

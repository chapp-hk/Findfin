package ch.app.hk.bank.locator.core.location.state

import androidx.compose.runtime.Stable

@Stable
sealed interface LocationSettingResult {
    data object Loading : LocationSettingResult

    data object Enabled : LocationSettingResult

    data object Disabled : LocationSettingResult

    data object PermissionDenied : LocationSettingResult

    data object NoSensor : LocationSettingResult
}

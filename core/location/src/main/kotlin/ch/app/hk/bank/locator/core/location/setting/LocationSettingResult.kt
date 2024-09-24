package ch.app.hk.bank.locator.core.location.setting

import androidx.compose.runtime.Stable

@Stable
sealed interface LocationSettingResult {
    data object None : LocationSettingResult

    data object Enabled : LocationSettingResult

    data object Disabled : LocationSettingResult

    data object NoSensor : LocationSettingResult
}

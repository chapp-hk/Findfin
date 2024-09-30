package ch.app.hk.bank.locator.core.location.state

import androidx.compose.runtime.Stable

@Stable
sealed interface LocationStateResult {
    data object Enabled : LocationStateResult

    data object Disabled : LocationStateResult

    data object NoSensor : LocationStateResult
}

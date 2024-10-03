package ch.app.hk.bank.locator.core.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import ch.app.hk.bank.locator.core.location.setting.helper.LocalSettingHelper
import ch.app.hk.bank.locator.core.location.setting.helper.SettingHelperImpl
import ch.app.hk.bank.locator.core.location.setting.state.LocationSettingState
import ch.app.hk.bank.locator.core.location.setting.state.rememberLocationSettingState

/**
 * A composable function that checks the location settings and provides content based on the result.
 *
 * @param onResult A callback function that is invoked with the current [LocationSettingState].
 * @param content A composable function that takes the current [LocationSettingState] and provides the UI content.
 *
 * Example usage:
 * ```
 * LocationSettingCheckContent(
 *     onResult = { state ->
 *         // Handle the location setting state
 *     }
 * ) { locationSettingState ->
 *     // Provide the UI content based on the location setting state
 * }
 * ```
 */
@Composable
fun LocationSettingCheckContent(
    onResult: (LocationSettingState) -> Unit,
    content: @Composable (LocationSettingState) -> Unit,
) {
    CompositionLocalProvider(
        value = LocalSettingHelper providesDefault SettingHelperImpl(context = LocalContext.current),
    ) {
        val locationSettingState =
            rememberLocationSettingState(
                settingHelper = LocalSettingHelper.current,
            ) { state ->
                onResult(state)
            }

        content(locationSettingState)
    }
}

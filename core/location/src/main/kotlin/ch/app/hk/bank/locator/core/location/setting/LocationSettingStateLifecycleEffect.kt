package ch.app.hk.bank.locator.core.location.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * A composable function that observes the lifecycle of the current lifecycle owner and refreshes
 * the location setting state when the specified lifecycle event occurs.
 *
 * @param locationSettingState The [MutableLocationSettingState] instance to be refreshed.
 * @param lifecycleEvent The [Lifecycle.Event] that triggers the refresh of the location setting state.
 *                        Defaults to [Lifecycle.Event.ON_RESUME].
 */
@Composable
internal fun LocationSettingStateLifecycleEffect(
    locationSettingState: MutableLocationSettingState,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_RESUME,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val locationStateCheckerObserver =
        remember(locationSettingState) {
            LifecycleEventObserver { _, event ->
                if (event == lifecycleEvent) {
                    locationSettingState.refreshLocationState()
                }
            }
        }

    DisposableEffect(lifecycle, locationStateCheckerObserver) {
        lifecycle.addObserver(locationStateCheckerObserver)
        onDispose {
            lifecycle.removeObserver(locationStateCheckerObserver)
        }
    }
}

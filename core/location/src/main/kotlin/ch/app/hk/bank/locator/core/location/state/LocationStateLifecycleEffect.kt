package ch.app.hk.bank.locator.core.location.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
internal fun LocationStateLifecycleEffect(
    locationSettingState: MutableLocationState,
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

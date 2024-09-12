package ch.app.hk.bank.locator.core.permission

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * A composable function that observes the lifecycle and checks the permission status when the lifecycle is resumed.
 *
 * Check if the permission was granted when the lifecycle is resumed.
 * The user might've gone to the Settings screen and granted the permission.
 *
 * If the permission is revoked, check again.
 * We don't check if the permission was denied as that triggers a process restart.
 *
 * @param permissionState The state of the permission to be checked.
 * @param lifecycleEvent The lifecycle event that triggers the permission check.
 * Default is [Lifecycle.Event.ON_RESUME].
 */
@Composable
internal fun PermissionLifecycleCheckerEffect(
    permissionState: MutablePermissionState,
    lifecycleEvent: Lifecycle.Event = Lifecycle.Event.ON_RESUME,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val permissionCheckerObserver =
        remember(permissionState) {
            LifecycleEventObserver { _, event ->
                if (event == lifecycleEvent) {
                    if (permissionState.result != PermissionResult.Granted) {
                        permissionState.refreshPermissionStatus()
                    }
                }
            }
        }

    DisposableEffect(lifecycle, permissionCheckerObserver) {
        lifecycle.addObserver(permissionCheckerObserver)
        onDispose {
            lifecycle.removeObserver(permissionCheckerObserver)
        }
    }
}

/**
 * Finds the [Activity] from a given [Context].
 *
 * @return The [Activity] associated with the context.
 * @throws IllegalStateException if the context is not associated with an activity.
 */
internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }

        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

/**
 * Checks if a specific permission is granted.
 *
 * @param permission The permission to check.
 * @return `true` if the permission is granted, `false` otherwise.
 */
internal fun Context.isPermissionGranted(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * Determines if the rationale for requesting a permission should be shown.
 *
 * @param permission The permission to check.
 * @return `true` if the rationale should be shown, `false` otherwise.
 */
internal fun Activity.shouldShowPermissionRationale(permission: String): Boolean =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

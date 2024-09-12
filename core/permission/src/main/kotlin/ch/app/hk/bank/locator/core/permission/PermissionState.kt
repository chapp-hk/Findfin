package ch.app.hk.bank.locator.core.permission

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

/**
 * Interface representing the state of a permission.
 */
@Stable
interface PermissionState {
    /**
     * The permission being requested.
     */
    val permission: String

    /**
     * The result of the permission request.
     */
    val result: PermissionResult

    /**
     * Launches the permission request.
     */
    fun launchPermissionRequest()
}

/**
 * Remembers the state of a permission.
 *
 * @param permission The permission to be requested.
 * @param onPermissionResult A callback to be invoked with the result of the permission request.
 * @return The remembered [PermissionState].
 */
@Composable
fun rememberPermissionState(
    permission: String,
    onPermissionResult: (Boolean) -> Unit = {},
): PermissionState {
    return rememberMutablePermissionState(permission, onPermissionResult)
}

/**
 * Internal implementation of [PermissionState] that can be modified.
 *
 * @property permission The permission being requested.
 * @property context The context in which the permission is being requested.
 * @property activity The activity in which the permission is being requested.
 */
@Stable
internal class MutablePermissionState(
    override val permission: String,
    private val context: Context,
    private val activity: Activity,
) : PermissionState {
    /**
     * The result of the permission request.
     */
    override var result: PermissionResult by mutableStateOf(getPermissionResult())

    /**
     * The launcher for the permission request.
     */
    internal var launcher: ActivityResultLauncher<String>? = null

    /**
     * Launches the permission request.
     */
    override fun launchPermissionRequest() {
        launcher?.launch(permission)
            ?: error("ActivityResultLauncher cannot be null")
    }

    /**
     * Refreshes the status of the permission.
     */
    internal fun refreshPermissionStatus() {
        result = getPermissionResult()
    }

    /**
     * Gets the result of the permission request.
     *
     * @return The [PermissionResult] of the permission request.
     */
    private fun getPermissionResult(): PermissionResult {
        return if (context.isPermissionGranted(permission)) {
            PermissionResult.Granted
        } else {
            PermissionResult.Denied(activity.shouldShowPermissionRationale(permission))
        }
    }
}

/**
 * Remembers the mutable state of a permission.
 *
 * @param permission The permission to be requested.
 * @param onPermissionResult A callback to be invoked with the result of the permission request.
 * @return The remembered [MutablePermissionState].
 */
@Composable
internal fun rememberMutablePermissionState(
    permission: String,
    onPermissionResult: (Boolean) -> Unit = {},
): MutablePermissionState {
    val context = LocalContext.current
    val permissionState =
        remember(permission) {
            MutablePermissionState(permission, context, context.findActivity())
        }

    // Refresh the permission status when the lifecycle is resumed
    PermissionLifecycleCheckerEffect(permissionState)

    // Remember RequestPermission launcher and assign it to permissionState
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionState.refreshPermissionStatus()
            onPermissionResult(it)
        }

    DisposableEffect(permissionState, launcher) {
        permissionState.launcher = launcher
        onDispose {
            permissionState.launcher = null
        }
    }

    return permissionState
}

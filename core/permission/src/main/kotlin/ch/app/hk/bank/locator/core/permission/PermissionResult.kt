package ch.app.hk.bank.locator.core.permission

import androidx.compose.runtime.Stable

/**
 * Represents the result of a permission request.
 *
 * This sealed interface has two possible states:
 * - [Granted]: The permission has been granted.
 * - [Denied]: The permission has been denied, with an optional rationale flag.
 */
@Stable
sealed interface PermissionResult {
    /**
     * Indicates that the permission has been granted.
     */
    data object Granted : PermissionResult

    /**
     * Indicates that the permission has been denied.
     *
     * @property shouldShowRationale A flag indicating whether the rationale for the permission should be shown.
     */
    data class Denied(val shouldShowRationale: Boolean) : PermissionResult
}

/**
 * Extension property to check if the permission result is granted.
 */
val PermissionResult.isGranted: Boolean
    get() = this == PermissionResult.Granted

/**
 * Extension property to check if the rationale for the permission should be shown.
 */
val PermissionResult.shouldShowRationale: Boolean
    get() =
        when (this) {
            PermissionResult.Granted -> false
            is PermissionResult.Denied -> shouldShowRationale
        }

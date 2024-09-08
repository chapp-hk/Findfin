package ch.app.hk.bank.locator.core.location.helper.permission

/**
 * Interface representing a helper for checking permissions.
 */
interface PermissionHelper {
    /**
     * Checks if the necessary permissions are granted.
     *
     * @return `true` if the permissions are granted, `false` otherwise.
     */
    fun checkPermission(): Boolean
}

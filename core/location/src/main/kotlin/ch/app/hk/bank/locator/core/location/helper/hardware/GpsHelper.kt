package ch.app.hk.bank.locator.core.location.helper.hardware

/**
 * Interface representing a helper for GPS-related functionalities.
 */
interface GpsHelper {
    /**
     * Checks if the device has a GPS sensor.
     *
     * @return `true` if the device has a GPS sensor, `false` otherwise.
     */
    fun hasGpsSensor(): Boolean

    /**
     * Checks if the GPS is enabled on the device.
     *
     * @return `true` if the GPS is enabled, `false` otherwise.
     */
    fun isGpsEnabled(): Boolean
}

package ch.app.hk.bank.locator.core.location.provider

import android.location.Location

/**
 * Interface representing a client for accessing location data.
 */
interface LocationProvider {
    /**
     * Retrieves the current location of the device.
     *
     * @return The current [Location] of the device, or `null` if the location could not be determined.
     * @throws SecurityException if the required location permissions are not granted.
     */
    suspend fun getCurrentLocation(): Location?
}

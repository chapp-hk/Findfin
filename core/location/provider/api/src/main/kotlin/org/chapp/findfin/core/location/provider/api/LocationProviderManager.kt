package org.chapp.findfin.core.location.provider.api

/**
 * Interface for providing location data.
 * Implementations of this interface should handle the retrieval of the current location.
 */
interface LocationProviderManager {
    /**
     * Retrieves the current location.
     *
     * @return [LocationResult] which can be a success with the location data,
     * an error, or an indication that the location is unavailable.
     */
    suspend fun getCurrentLocation(): LocationResult
}

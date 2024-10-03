package ch.app.hk.bank.locator.core.location.provider

/**
 * Interface for providing location data.
 * Implementations of this interface should handle the retrieval of the current location.
 */
interface LocationProvider {
    /**
     * Retrieves the current location.
     *
     * @return [LocationProviderResult] which can be a success with the location data,
     * an error, or an indication that the location is unavailable.
     */
    suspend fun getCurrentLocation(): LocationProviderResult
}

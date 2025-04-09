package org.chapp.findfin.core.location.provider.api

/**
 * Represents the result of a location request.
 */
sealed interface LocationProviderResult {
    /**
     * Indicates that the location is unavailable.
     */
    data object LocationUnavailable : LocationProviderResult

    /**
     * Indicates that an error occurred while retrieving the location.
     */
    data object Error : LocationProviderResult

    /**
     * Indicates that the location was successfully retrieved.
     *
     * @property position The retrieved location.
     */
    data class Success(val position: Position) : LocationProviderResult
}

package org.chapp.findfin.core.location.provider.api

/**
 * Represents the result of a location request.
 */
sealed interface LocationResult {
    /**
     * Indicates that the location is unavailable.
     */
    data object LocationUnavailable : LocationResult

    /**
     * Indicates that an error occurred while retrieving the location.
     */
    data object Error : LocationResult

    /**
     * Indicates that the location was successfully retrieved.
     *
     * @property location The retrieved location.
     */
    data class Success(val location: Location) : LocationResult
}

data class Location(
    val latitude: Double,
    val longitude: Double,
)

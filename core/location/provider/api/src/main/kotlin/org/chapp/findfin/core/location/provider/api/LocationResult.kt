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

/**
 * Represents a geographical location with latitude and longitude coordinates.
 *
 * @property latitude The latitude of the location in degrees.
 * @property longitude The longitude of the location in degrees.
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
)

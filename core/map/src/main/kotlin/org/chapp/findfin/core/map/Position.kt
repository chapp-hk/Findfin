package org.chapp.findfin.core.map

/**
 * Data class representing a geographical position with latitude and longitude.
 *
 * @property latitude The latitude of the position.
 * @property longitude The longitude of the position.
 */
data class Position(
    val latitude: Double,
    val longitude: Double,
)

/**
 * Represents the geographical bounds defined by the northeast and southwest corners.
 *
 * @property northeast The northeast(top-right) corner of the bounds as a [Position].
 * @property southwest The southwest(bottom-left) corner of the bounds as a [Position].
 */
data class PositionBounds(
    val northeast: Position,
    val southwest: Position,
)

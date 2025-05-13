package org.chapp.findfin.feature.bank.data.repo.model

/**
 * Represents a geographical boundary defined by minimum and maximum latitude and longitude values.
 *
 * @property minLat The minimum latitude of the boundary.
 * @property maxLat The maximum latitude of the boundary.
 * @property minLong The minimum longitude of the boundary.
 * @property maxLong The maximum longitude of the boundary.
 */
data class BankLocationBound(
    val minLat: Double,
    val maxLat: Double,
    val minLong: Double,
    val maxLong: Double,
)

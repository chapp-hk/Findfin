package org.chapp.findfin.feature.bank.data.repo.model

/**
 * Represents a geographical boundary defined by minimum and maximum latitude and longitude values.
 *
 * @property minLatitude The minimum latitude of the boundary.
 * @property maxLatitude The maximum latitude of the boundary.
 * @property minLongitude The minimum longitude of the boundary.
 * @property maxLongitude The maximum longitude of the boundary.
 */
data class BankLocationBound(
    val minLatitude: Double,
    val maxLatitude: Double,
    val minLongitude: Double,
    val maxLongitude: Double,
)

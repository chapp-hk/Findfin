package org.chapp.findfin.feature.bank.data.repo.model

/**
 * Represents a bank entity with detailed information.
 *
 * @property type The type of the bank as a string (e.g., ATM, BRANCH).
 * @property district The district where the bank is located.
 * @property bankName The name of the bank.
 * @property typeName The descriptive name of the bank type.
 * @property address The address of the bank.
 * @property serviceHours The service hours of the bank.
 * @property latitude The latitude coordinate of the bank's location.
 * @property longitude The longitude coordinate of the bank's location.
 */
data class BankModel(
    val type: String,
    val district: String,
    val bankName: String,
    val typeName: String,
    val address: String,
    val serviceHours: String,
    val latitude: Double,
    val longitude: Double,
)

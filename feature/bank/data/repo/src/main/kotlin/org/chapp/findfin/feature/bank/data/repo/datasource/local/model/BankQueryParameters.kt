package org.chapp.findfin.feature.bank.data.repo.datasource.local.model

data class BankQueryParameters(
    val language: String,
    val bankName: String? = null,
    val type: String? = null,
    val minLatitude: Double? = null,
    val maxLatitude: Double? = null,
    val minLongitude: Double? = null,
    val maxLongitude: Double? = null,
)

package org.chapp.findfin.feature.bank.data.repo.datasource.local.model

data class BankQueryParameters(
    val language: String,
    val bankName: String? = null,
    val type: String? = null,
    val minLat: Double? = null,
    val maxLat: Double? = null,
    val minLon: Double? = null,
    val maxLon: Double? = null,
)

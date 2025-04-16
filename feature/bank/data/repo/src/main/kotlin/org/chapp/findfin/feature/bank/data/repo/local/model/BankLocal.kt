package org.chapp.findfin.feature.bank.data.repo.local.model

data class BankLocal(
    val language: String,
    val type: String,
    val district: String,
    val bankName: String,
    val typeName: String,
    val address: String,
    val serviceHours: String,
    val latitude: Double,
    val longitude: Double,
)

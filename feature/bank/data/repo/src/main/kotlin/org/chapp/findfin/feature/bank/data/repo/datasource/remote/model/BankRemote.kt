package org.chapp.findfin.feature.bank.data.repo.datasource.remote.model

interface BankRemote {
    val district: String
    val bankName: String
    val typeName: String
    val address: String
    val serviceHours: String
    val latitude: Double
    val longitude: Double
}

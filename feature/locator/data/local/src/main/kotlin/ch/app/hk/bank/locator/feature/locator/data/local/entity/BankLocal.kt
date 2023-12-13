package ch.app.hk.bank.locator.feature.locator.data.local.entity

interface BankLocal {
    val type: String
    val district: String
    val bankName: String
    val typeName: String
    val address: String
    val serviceHours: String
    val latitude: Double
    val longitude: Double
}

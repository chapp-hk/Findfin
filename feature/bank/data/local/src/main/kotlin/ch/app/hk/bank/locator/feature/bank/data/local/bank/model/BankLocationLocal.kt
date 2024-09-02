package ch.app.hk.bank.locator.feature.bank.data.local.bank.model

data class BankLocationLocal(
    val type: String,
    val district: String,
    val bankName: String,
    val typeName: String,
    val address: String,
    val serviceHours: String,
    val latitude: Double,
    val longitude: Double,
)

package ch.app.hk.bank.locator.feature.bank.data.repo.location.local.model

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

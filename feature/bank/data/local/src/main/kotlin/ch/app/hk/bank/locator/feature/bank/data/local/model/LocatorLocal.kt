package ch.app.hk.bank.locator.feature.bank.data.local.model

data class LocatorLocal(
    val type: String,
    val district: String,
    val bankName: String,
    val typeName: String,
    val address: String,
    val serviceHours: String,
    val latitude: Double,
    val longitude: Double,
)

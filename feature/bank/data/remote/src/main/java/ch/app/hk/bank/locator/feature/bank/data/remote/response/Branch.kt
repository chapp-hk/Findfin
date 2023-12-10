package ch.app.hk.bank.locator.feature.bank.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Branch(
    @SerialName("district")
    val district: String?,
    @SerialName("bank_name")
    val bankName: String,
    @SerialName("type_of_machine")
    val typeOfMachine: String,
    @SerialName("address")
    val address: String,
    @SerialName("service_hours")
    val serviceHours: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
)

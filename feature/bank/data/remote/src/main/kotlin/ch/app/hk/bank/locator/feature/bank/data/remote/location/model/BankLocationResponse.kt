package ch.app.hk.bank.locator.feature.bank.data.remote.location.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class BankLocationResponse(
    @SerialName("district")
    val district: String = "",
    @SerialName("bank_name")
    val bankName: String = "",
    @JsonNames(
        "branch_name",
        "type_of_machine",
    )
    val typeName: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("service_hours")
    val serviceHours: String = "",
    @SerialName("latitude")
    val latitude: Double = 0.0,
    @SerialName("longitude")
    val longitude: Double = 0.0,
)

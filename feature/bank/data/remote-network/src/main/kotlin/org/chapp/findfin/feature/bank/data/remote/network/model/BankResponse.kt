package org.chapp.findfin.feature.bank.data.remote.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class BankResponse(
    val district: String = "",
    @SerialName("bank_name")
    val bankName: String = "",
    @JsonNames(
        "branch_name",
        "type_of_machine",
    )
    val typeName: String = "",
    val address: String = "",
    @SerialName("service_hours")
    val serviceHours: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)

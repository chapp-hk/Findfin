package org.chapp.findfin.feature.bank.data.remote.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import org.chapp.findfin.feature.bank.data.repo.datasource.remote.model.BankRemote

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class BankResponse(
    @SerialName("district")
    override val district: String = "",
    @SerialName("bank_name")
    override val bankName: String = "",
    @JsonNames(
        "branch_name",
        "type_of_machine",
    )
    override val typeName: String = "",
    @SerialName("address")
    override val address: String = "",
    @SerialName("service_hours")
    override val serviceHours: String = "",
    @SerialName("latitude")
    override val latitude: Double = 0.0,
    @SerialName("longitude")
    override val longitude: Double = 0.0,
) : BankRemote

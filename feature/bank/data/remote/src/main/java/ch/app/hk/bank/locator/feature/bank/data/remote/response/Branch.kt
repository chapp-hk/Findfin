package ch.app.hk.bank.locator.feature.bank.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Branch(
    @SerialName("district")
    val district: String = "",
    @SerialName("bank_name")
    val bankName: String = "",
    @SerialName("branch_name")
    val branchName: String = "",
    @SerialName("address")
    val address: String = "",
    @SerialName("service_hours")
    val serviceHours: String = "",
    @SerialName("latitude")
    val latitude: Double = 0.0,
    @SerialName("longitude")
    val longitude: Double = 0.0,
)

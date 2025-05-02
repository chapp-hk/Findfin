package org.chapp.findfin.feature.bank.data.remote.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    @SerialName("header")
    val header: Header?,
    @SerialName("result")
    val result: Result<T>?,
) {
    @Serializable
    data class Header(
        @SerialName("success")
        val success: Boolean = false,
        @SerialName("err_code")
        val errorCode: String = "",
        @SerialName("err_msg")
        val errorMessage: String = "",
    )

    @Serializable
    data class Result<T>(
        @SerialName("datasize")
        val dataSize: Int = 0,
        @SerialName("records")
        val records: List<T> = emptyList(),
    )
}

package org.chapp.findfin.feature.bank.data.remote.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val header: Header?,
    val result: Result<T>?,
) {
    @Serializable
    data class Header(
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
        val records: List<T> = emptyList(),
    )
}

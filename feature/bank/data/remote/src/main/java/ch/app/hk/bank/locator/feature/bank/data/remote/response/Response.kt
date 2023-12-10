package ch.app.hk.bank.locator.feature.bank.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("header")
    val header: Header,
) {
    @Serializable
    data class Header(
        @SerialName("success")
        val success: Boolean,
        @SerialName("err_code")
        val errorCode: String,
        @SerialName("err_msg")
        val errorMessage: String,
    )

    @Serializable
    data class Result(
        @SerialName("datasize")
        val dataSize: Int,
        @SerialName("records")
        val records: List<Int>,
    )
}

package ch.app.hk.bank.locator.feature.auth.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthNavGraphDestination(
    @SerialName("shouldCheckIsInit")
    val shouldCheckIsInit: Boolean,
)

@Serializable
object AuthEntryDestination

@Serializable
object AuthRegisterDestination

@Serializable
object AuthLoginDestination

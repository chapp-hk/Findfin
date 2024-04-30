package ch.app.hk.bank.locator.feature.auth.navigation

import ch.app.hk.bank.locator.core.navigation.Destination

object AuthNavGraphDestination : Destination() {
    override val navGraphId: String = "auth-initialize-destination"
    override val route: String = "auth-route"
}

object AuthEntryDestination : Destination() {
    override val navGraphId: String = ""
    override val route: String = "auth-entry-route"
}

object AuthRegisterDestination : Destination() {
    override val navGraphId: String = ""
    override val route: String = "auth-register-route"
}

object AuthLoginDestination : Destination() {
    override val navGraphId: String = ""
    override val route: String = "auth-login-route"
}

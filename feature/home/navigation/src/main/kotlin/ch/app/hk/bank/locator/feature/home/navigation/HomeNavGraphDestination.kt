package ch.app.hk.bank.locator.feature.home.navigation

import ch.app.hk.bank.locator.core.navigation.Destination

object HomeNavGraphDestination : Destination() {
    override val navGraphId: String = "home-graph-id"
    override val route: String = "home-graph-route"
}

object HomeDestination : Destination() {
    override val navGraphId: String = ""
    override val route: String = "home-route"
}

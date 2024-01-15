package ch.app.hk.bank.locator.feature.onboarding.navigation

import ch.app.hk.bank.locator.core.navigation.Destination

object LandingNavGraphDestination : Destination() {
    override val navGraphId: String = "landing-destination"
    override val route: String = "landing-route"
}

object LandingDestination : Destination() {
    override val navGraphId: String = ""
    override val route: String = "landing"
}

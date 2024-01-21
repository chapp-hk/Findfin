package ch.app.hk.bank.locator.feature.onboarding.navigation

import ch.app.hk.bank.locator.core.navigation.Destination

object OnboardingNavGraphDestination : Destination() {
    override val navGraphId: String = "onboarding-destination"
    override val route: String = "onboarding-route"
}

object OnboardingDestination : Destination() {
    override val navGraphId: String = ""
    override val route: String = "onboarding"
}

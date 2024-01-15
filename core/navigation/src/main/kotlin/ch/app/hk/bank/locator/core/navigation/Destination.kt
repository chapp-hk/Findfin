package ch.app.hk.bank.locator.core.navigation

import androidx.navigation.NamedNavArgument

abstract class Destination {
    /**
     * Defines a specific navigation graph ID.
     * This is needed when using nested graphs via the navigation DLS, to differentiate a specific
     * destination's route from the route of the entire nested graph it belongs to.
     */
    abstract val navGraphId: String

    /**
     * Defines a specific route this destination belongs to.
     * Route is a String that defines the path to your composable.
     * You can think of it as an implicit deep link that leads to a specific destination.
     * Each destination should have a unique route.
     */
    abstract val route: String

    /**
     * Defines a list of arguments that belongs to this destination
     */
    open val arguments: List<NamedNavArgument> = listOf()
}

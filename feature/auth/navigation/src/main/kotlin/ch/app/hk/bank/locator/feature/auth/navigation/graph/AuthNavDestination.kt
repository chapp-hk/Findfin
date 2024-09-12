package ch.app.hk.bank.locator.feature.auth.navigation.graph

import kotlinx.serialization.Serializable

/**
 * Represents the destination for the onboarding navigation graph in the authentication flow.
 *
 * This object is used as a marker for the start of the authentication onboarding navigation graph.
 */
@Serializable
object AuthOnboardingNavGraphDestination

/**
 * Represents the destination for the main navigation graph in the authentication flow.
 *
 * This object is used as a marker for the start of the main authentication navigation graph.
 */
@Serializable
object AuthNavGraphDestination

/**
 * Represents the destination for the registration screen in the authentication flow.
 *
 * This object is used as a marker for the screen where users can register a new account.
 */
@Serializable
object AuthRegisterDestination

/**
 * Represents the destination for the login screen in the authentication flow.
 *
 * This object is used as a marker for the screen where users can log in to their account.
 */
@Serializable
object AuthLoginDestination

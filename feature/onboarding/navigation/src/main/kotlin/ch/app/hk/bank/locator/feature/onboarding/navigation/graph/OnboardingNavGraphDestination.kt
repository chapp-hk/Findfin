package ch.app.hk.bank.locator.feature.onboarding.navigation.graph

import kotlinx.serialization.Serializable

/**
 * Represents the destination for the onboarding navigation graph.
 *
 * This object is used as a marker for the start of the onboarding navigation graph.
 */
@Serializable
object OnboardingNavGraphDestination

/**
 * Represents the destination for selecting a language during onboarding.
 *
 * This object is used as a marker for the screen where users can select their preferred language.
 */
@Serializable
object OnboardingSelectLanguageDestination

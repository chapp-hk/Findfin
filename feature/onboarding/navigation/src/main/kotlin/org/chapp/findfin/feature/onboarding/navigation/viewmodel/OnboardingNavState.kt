package org.chapp.findfin.feature.onboarding.navigation.viewmodel

internal sealed interface OnboardingNavState {
    data object Loading : OnboardingNavState

    data object IsFinishedOnboard : OnboardingNavState

    data object NotFinishedOnboard : OnboardingNavState
}

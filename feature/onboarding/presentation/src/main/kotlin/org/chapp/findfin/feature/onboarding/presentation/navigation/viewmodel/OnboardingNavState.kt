package org.chapp.findfin.feature.onboarding.presentation.navigation.viewmodel

internal sealed interface OnboardingNavState {
    data object Loading : OnboardingNavState

    data object IsFinishedOnboard : OnboardingNavState

    data object NotFinishedOnboard : OnboardingNavState
}

package org.chapp.findfin.feature.onboarding.presentation.ui.language.state

internal sealed interface SelectLanguageUiState {
    data object Initial : SelectLanguageUiState

    data object Loading : SelectLanguageUiState

    data class Success(val selectedLanguageTag: String) : SelectLanguageUiState

    data class Error(val selectedLanguageTag: String) : SelectLanguageUiState
}

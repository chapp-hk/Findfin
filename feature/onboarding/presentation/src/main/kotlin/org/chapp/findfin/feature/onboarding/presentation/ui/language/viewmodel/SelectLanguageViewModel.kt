package org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel

import org.chapp.findfin.core.design.ui.foundation.ScreenStateFlow
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState

internal interface SelectLanguageViewModel {
    val availableLanguages: List<SelectLanguageUiModel>
    val uiState: ScreenStateFlow<SelectLanguageUiState, String>

    fun setLanguage(language: String)
}

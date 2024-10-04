package org.chapp.findfin.feature.onboarding.ui.language.viewmodel

import org.chapp.findfin.core.design.ui.ScreenStateFlow
import org.chapp.findfin.feature.onboarding.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.ui.language.state.SelectLanguageUiState

interface SelectLanguageViewModel {
    val availableLanguages: List<SelectLanguageUiModel>
    val uiState: ScreenStateFlow<SelectLanguageUiState, String>

    fun setLanguage(language: String)
}

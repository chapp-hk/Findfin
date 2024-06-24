package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState

interface SelectLanguageViewModel {
    val availableLanguages: List<SelectLanguageUiModel>
    val uiState: ScreenStateFlow<SelectLanguageUiState, String>

    fun setLanguage(language: String)
}

package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import ch.app.hk.bank.locator.core.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState
import kotlinx.coroutines.flow.StateFlow

interface SelectLanguageViewModel {
    val availableLanguages: List<SelectLanguageUiModel>
    val uiState: StateFlow<ScreenState<SelectLanguageUiState>>

    fun setLanguage(language: String)
}

package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import kotlinx.coroutines.flow.StateFlow

interface SelectLanguageViewModel {
    val availableLanguages: List<SelectLanguageUiModel>
    val uiState: StateFlow<ScreenState<Unit>>
    fun setLanguage(language: String)
}

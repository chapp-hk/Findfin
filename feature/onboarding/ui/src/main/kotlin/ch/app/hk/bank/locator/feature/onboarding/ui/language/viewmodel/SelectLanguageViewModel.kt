package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel

interface SelectLanguageViewModel {
    val availableLanguages: List<SelectLanguageUiModel>
    fun setLanguage(language: String)
}

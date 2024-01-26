package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import androidx.lifecycle.ViewModel
import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltViewModel
class SelectLanguageViewModelImpl @Inject constructor(
    private val appLocaleRepository: AppLocaleRepository,
) : SelectLanguageViewModel, ViewModel() {
    private val selectLanguageUiModelMapper =
        Mappers.getMapper(SelectLanguageUiModelMapper::class.java)

    override val availableLanguages: List<SelectLanguageUiModel> =
        appLocaleRepository.availableLocales().map(selectLanguageUiModelMapper::clone)

    override fun setLanguage(language: String) {
        appLocaleRepository.setLocale(language)
    }
}

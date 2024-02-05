package ch.app.hk.bank.locator.feature.onboarding.ui.language.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.locale.api.AppLocaleRepository
import ch.app.hk.bank.locator.feature.onboarding.domain.fetch.usecase.FetchAllLocatorsWithLanguageUseCase
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModel
import ch.app.hk.bank.locator.feature.onboarding.ui.language.model.SelectLanguageUiModelMapper
import ch.app.hk.bank.locator.feature.onboarding.ui.language.state.SelectLanguageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltViewModel
class SelectLanguageViewModelImpl @Inject constructor(
    private val appLocaleRepository: AppLocaleRepository,
    private val fetchAllLocatorsWithLanguage: FetchAllLocatorsWithLanguageUseCase,
) : SelectLanguageViewModel, ViewModel() {
    private val _uiState = MutableStateFlow<ScreenState<SelectLanguageUiState>>(ScreenState.Empty)
    override val uiState = _uiState.asStateFlow()

    private val selectLanguageUiModelMapper =
        Mappers.getMapper(SelectLanguageUiModelMapper::class.java)

    override val availableLanguages: List<SelectLanguageUiModel> =
        appLocaleRepository.availableLocales().map(selectLanguageUiModelMapper::clone)

    override fun setLanguage(language: String) {
        appLocaleRepository.setLocale(language)
        viewModelScope.launch {
            _uiState.emit(ScreenState.Loading)
            if (fetchAllLocatorsWithLanguage()) {
                _uiState.emit(ScreenState.Success(SelectLanguageUiState(language)))
            } else {
                _uiState.emit(ScreenState.Error(Error(), SelectLanguageUiState(language)))
            }
        }
    }
}

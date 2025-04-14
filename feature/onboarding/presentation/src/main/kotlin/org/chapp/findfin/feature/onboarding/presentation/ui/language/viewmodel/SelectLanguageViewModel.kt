package org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.locale.api.AppLocaleManager
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModelMapper
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState
import org.chapp.findfin.feature.setting.data.repo.language.repository.LanguageRepository
import org.chapp.findfin.feature.setting.domain.fetch.usecase.FetchAllBankLocationsWithLanguageUseCase
import javax.inject.Inject

@HiltViewModel
internal class SelectLanguageViewModel @Inject constructor(
    languageRepository: LanguageRepository,
    private val appLocaleManager: org.chapp.findfin.core.locale.api.AppLocaleManager,
    private val fetchAllLocatorsWithLanguage: FetchAllBankLocationsWithLanguageUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<SelectLanguageUiState>(SelectLanguageUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private val selectLanguageUiModelMapper = SelectLanguageUiModelMapper()

    val availableLanguages: List<SelectLanguageUiModel> =
        languageRepository.getAvailableLanguages().map(selectLanguageUiModelMapper::map)

    fun setLanguage(language: String) {
        appLocaleManager.setLocale(language)
        viewModelScope.launch {
            _uiState.emit(SelectLanguageUiState.Loading)
            if (fetchAllLocatorsWithLanguage()) {
                _uiState.emit(SelectLanguageUiState.Success(language))
            } else {
                _uiState.emit(SelectLanguageUiState.Error(language))
            }
        }
    }
}

package org.chapp.findfin.feature.onboarding.presentation.ui.language.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.design.ui.ScreenState
import org.chapp.findfin.core.design.ui.mutableScreenStateFlowOf
import org.chapp.findfin.core.locale.api.AppLocaleRepository
import org.chapp.findfin.feature.onboarding.domain.fetch.usecase.FetchAllBankLocationsWithLanguageUseCase
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModel
import org.chapp.findfin.feature.onboarding.presentation.ui.language.model.SelectLanguageUiModelMapper
import org.chapp.findfin.feature.onboarding.presentation.ui.language.state.SelectLanguageUiState
import org.mapstruct.factory.Mappers
import javax.inject.Inject

@HiltViewModel
internal class SelectLanguageViewModelImpl @Inject constructor(
    private val appLocaleRepository: AppLocaleRepository,
    private val fetchAllLocatorsWithLanguage: FetchAllBankLocationsWithLanguageUseCase,
) : SelectLanguageViewModel, ViewModel() {
    private val _uiState = mutableScreenStateFlowOf<SelectLanguageUiState, String>(ScreenState.Empty)
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
                _uiState.emit(
                    ScreenState.Success<SelectLanguageUiState, Nothing>(
                        SelectLanguageUiState(language),
                    ),
                )
            } else {
                _uiState.emit(ScreenState.Error<Nothing, String>(language))
            }
        }
    }
}

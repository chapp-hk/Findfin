package org.chapp.findfin.feature.home.presentation.ui.nearby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.locale.AppLocaleManager
import org.chapp.findfin.feature.home.domain.nearby.model.NearByResult
import org.chapp.findfin.feature.home.domain.nearby.usecase.GetNearByServicesUseCase
import org.chapp.findfin.feature.home.presentation.ui.nearby.model.NearByItemUiModel
import org.chapp.findfin.feature.home.presentation.ui.nearby.model.NearByUiState
import javax.inject.Inject

@HiltViewModel
internal class NearByViewModelImpl @Inject constructor(
    private val appLocaleManager: AppLocaleManager,
    private val getNearByBanks: GetNearByServicesUseCase,
) : NearByViewModel, ViewModel() {
    private var currentJob: Job? = null
    private val _uiState = MutableStateFlow<NearByUiState>(NearByUiState.Loading)
    override val uiState = _uiState.asStateFlow()

    override fun getNearByServices() {
        currentJob?.cancel()
        currentJob =
            viewModelScope.launch {
                val language = appLocaleManager.getCurrentLocale()?.language.orEmpty()
                when (val result = getNearByBanks(language = language)) {
                    is NearByResult.UnknownError ->
                        _uiState.emit(NearByUiState.Error)

                    is NearByResult.Location -> {
                        if (result.list.isEmpty()) {
                            _uiState.emit(NearByUiState.Empty)
                        } else {
                            val itemList =
                                result.list.map {
                                    NearByItemUiModel(
                                        name = it.bankName,
                                        address = it.address,
                                        isFavourite = false,
                                    )
                                }
                            _uiState.emit(NearByUiState.Service(itemList))
                        }
                    }
                }
            }
    }
}

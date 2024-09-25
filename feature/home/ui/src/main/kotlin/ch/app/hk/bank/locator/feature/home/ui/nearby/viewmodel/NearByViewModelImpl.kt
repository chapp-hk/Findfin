package ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult
import ch.app.hk.bank.locator.feature.home.domain.nearby.usecase.GetNearByServicesUseCase
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByItemUiModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearByViewModelImpl @Inject constructor(
    private val getNearByBanks: GetNearByServicesUseCase,
) : NearByViewModel, ViewModel() {
    private val _uiState = MutableStateFlow<NearByUiState>(NearByUiState.Loading)
    override val uiState = _uiState.asStateFlow()

    init {
        getNearByServices()
    }

    override fun getNearByServices() {
        viewModelScope.launch {
            when (val result = getNearByBanks()) {
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

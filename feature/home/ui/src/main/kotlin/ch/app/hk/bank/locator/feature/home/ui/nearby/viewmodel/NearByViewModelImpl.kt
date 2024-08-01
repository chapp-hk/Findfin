package ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.core.design.ui.mutableScreenStateFlowOf
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult
import ch.app.hk.bank.locator.feature.home.domain.nearby.usecase.GetNearByServicesUseCase
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByError
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByItemUiModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearByViewModelImpl @Inject constructor(
    private val getNearByBanks: GetNearByServicesUseCase,
) : NearByViewModel, ViewModel() {
    private val _uiState =
        mutableScreenStateFlowOf<NearByUiState.Service, NearByUiState.Error>(ScreenState.Loading)
    override val uiState: ScreenStateFlow<NearByUiState.Service, NearByUiState.Error> =
        _uiState.asStateFlow()

    init {
        getNearByServices()
    }

    override fun getNearByServices() {
        viewModelScope.launch {
            _uiState.emit(ScreenState.Loading)
            when (val result = getNearByBanks()) {
                is NearByResult.PermissionNotGranted ->
                    _uiState.emit(ScreenState.Error(NearByUiState.Error(NearByError.PERMISSION_NOT_GRANTED)))

                is NearByResult.GpsNotSupported ->
                    _uiState.emit(ScreenState.Error(NearByUiState.Error(NearByError.GPS_NOT_SUPPORTED)))

                is NearByResult.GpsIsOff ->
                    _uiState.emit(ScreenState.Error(NearByUiState.Error(NearByError.GPS_IS_OFF)))

                is NearByResult.UnknownError ->
                    _uiState.emit(ScreenState.Error(NearByUiState.Error(NearByError.UNKNOWN_ERROR)))

                is NearByResult.Location -> {
                    if (result.list.isEmpty()) {
                        _uiState.emit(ScreenState.Empty)
                    } else {
                        val itemList =
                            result.list.map {
                                NearByItemUiModel(
                                    name = it.bankName,
                                    address = it.address,
                                    isFavourite = false,
                                )
                            }
                        _uiState.emit(ScreenState.Success(NearByUiState.Service(itemList)))
                    }
                }
            }
        }
    }
}

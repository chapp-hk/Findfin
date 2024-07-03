package ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.home.domain.nearby.model.NearByResult
import ch.app.hk.bank.locator.feature.home.domain.nearby.usecase.GetNearByServicesUseCase
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByError
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByItemUiModel
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NearByViewModelImpl @Inject constructor(
    private val getNearByBanks: GetNearByServicesUseCase,
) : NearByViewModel, ViewModel() {
    override val uiState: ScreenStateFlow<NearByUiState.Service, NearByUiState.Error> =
        flow<ScreenState<NearByUiState.Service, NearByUiState.Error>> {
            when (val result = getNearByBanks()) {
                is NearByResult.PermissionNotGranted ->
                    emit(ScreenState.Error(NearByUiState.Error(NearByError.PERMISSION_NOT_GRANTED)))

                is NearByResult.GpsNotSupported ->
                    emit(ScreenState.Error(NearByUiState.Error(NearByError.GPS_NOT_SUPPORTED)))

                is NearByResult.GpsIsOff ->
                    emit(ScreenState.Error(NearByUiState.Error(NearByError.GPS_IS_OFF)))

                is NearByResult.UnknownError ->
                    emit(ScreenState.Error(NearByUiState.Error(NearByError.UNKNOWN_ERROR)))

                is NearByResult.Location -> {
                    val itemList =
                        result.list.map {
                            NearByItemUiModel(
                                name = it.bankName,
                                address = it.address,
                                isFavourite = false,
                            )
                        }
                    emit(ScreenState.Success(NearByUiState.Service(itemList)))
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ScreenState.Loading,
        )
}

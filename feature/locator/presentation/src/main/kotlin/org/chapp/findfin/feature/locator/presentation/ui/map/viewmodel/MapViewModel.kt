package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.PositionBounds
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.model.BankType
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import org.chapp.findfin.feature.locator.presentation.navigation.MapBottomTabDestination
import org.chapp.findfin.feature.locator.presentation.ui.map.model.BankModelMapper
import org.chapp.findfin.feature.locator.presentation.ui.map.model.toBankType
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bankRepository: BankRepository,
) : ViewModel() {
    private var job: Job? = null
    private val mapRouteData = savedStateHandle.toRoute<MapBottomTabDestination>()

    private val _uiState = MutableStateFlow<List<MapMarker<BankType>>>(listOf())
    val uiState = _uiState.asStateFlow()

    fun getBanksWithinBound(bound: PositionBounds) {
        job?.cancel()
        job =
            viewModelScope.launch {
                val mapper = BankModelMapper()
                val banks =
                    bankRepository.getBanksByParameters(
                        type = mapRouteData.searchType.toBankType(),
                        bound =
                            BankLocationBound(
                                minLatitude = bound.southwest.latitude,
                                minLongitude = bound.southwest.longitude,
                                maxLatitude = bound.northeast.latitude,
                                maxLongitude = bound.northeast.longitude,
                            ),
                    )

                _uiState.emit(banks.map(mapper::toMapMarker))
            }
    }
}

package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.PositionBounds
import org.chapp.findfin.feature.bank.data.repo.model.BankLocationBound
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    private val bankRepository: BankRepository,
) : ViewModel() {
    private var job: Job? = null

    private val _uiState = MutableStateFlow<List<MapMarker>>(listOf())
    val uiState = _uiState.asStateFlow()

    fun getBanksWithinBound(bound: PositionBounds) {
        job?.cancel()
        job =
            viewModelScope.launch {
                val mapper = BankLocationModelMapper()
                val banks =
                    bankRepository.getBanksByParameters(
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

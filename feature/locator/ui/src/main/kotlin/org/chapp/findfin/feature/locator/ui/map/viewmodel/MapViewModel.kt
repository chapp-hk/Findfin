package org.chapp.findfin.feature.locator.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.core.map.Position
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val bankLocationRepository: BankLocationRepository,
) : ViewModel() {
    val uiState: StateFlow<List<MapMarker>> =
        flow {
            emit(
                bankLocationRepository.getAll().map {
                    MapMarker(
                        itemPosition = Position(latitude = it.latitude, longitude = it.longitude),
                        itemTitle = it.address,
                        itemSnippet = "",
                        itemZIndex = 0f,
                    )
                },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList(),
        )
}

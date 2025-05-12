package org.chapp.findfin.feature.locator.presentation.ui.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.chapp.findfin.core.map.MapMarker
import org.chapp.findfin.feature.bank.data.repo.repository.BankRepository
import javax.inject.Inject

@HiltViewModel
internal class MapViewModel @Inject constructor(
    private val bankRepository: BankRepository,
) : ViewModel() {
    val uiState: StateFlow<List<MapMarker>> =
        flow {
            val mapper = BankLocationModelMapper()
            emit(bankRepository.getBanksByParameters().map(mapper::toMapMarker))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList(),
        )
}

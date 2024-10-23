package org.chapp.findfin.feature.bank.ui.banklist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.chapp.findfin.core.design.ui.foundation.ScreenState
import org.chapp.findfin.core.design.ui.foundation.ScreenStateFlow
import org.chapp.findfin.feature.bank.data.repo.location.repository.BankLocationRepository
import javax.inject.Inject

@HiltViewModel
class BankListViewModelImpl @Inject constructor(
    private val bankLocationRepository: BankLocationRepository,
) : ViewModel(), BankListViewModel {
    override val screenState: ScreenStateFlow<List<String>, Nothing> =
        flow<ScreenState<List<String>, Nothing>> {
            emit(ScreenState.Success(bankLocationRepository.getAllBanks()))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ScreenState.Loading,
        )
}

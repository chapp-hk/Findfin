package org.chapp.findfin.feature.bank.presentation.ui.banklist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import org.chapp.findfin.feature.bank.data.repo.repository.BankLocationRepository
import javax.inject.Inject

@HiltViewModel
internal class BankListViewModelImpl @Inject constructor(
    private val bankLocationRepository: BankLocationRepository,
) : ViewModel(), BankListViewModel {
    override val screenState: StateFlow<List<String>> =
        flow {
            emit(bankLocationRepository.getAllBanks())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList(),
        )
}

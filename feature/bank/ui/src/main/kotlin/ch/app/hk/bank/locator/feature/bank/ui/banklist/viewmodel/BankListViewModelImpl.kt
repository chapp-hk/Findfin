package ch.app.hk.bank.locator.feature.bank.ui.banklist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.app.hk.bank.locator.core.design.ui.ScreenState
import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.bank.data.repo.repository.LocatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BankListViewModelImpl @Inject constructor(
    private val locatorRepository: LocatorRepository,
) : ViewModel(), BankListViewModel {
    override val screenState: ScreenStateFlow<List<String>, Nothing> =
        flow<ScreenState<List<String>, Nothing>> {
            emit(ScreenState.Success(locatorRepository.getAllBanks()))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ScreenState.Loading,
        )
}

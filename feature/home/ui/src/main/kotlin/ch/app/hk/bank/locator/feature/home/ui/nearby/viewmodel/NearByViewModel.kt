package ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel

import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState
import kotlinx.coroutines.flow.StateFlow

interface NearByViewModel {
    val uiState: StateFlow<NearByUiState>

    fun getNearByServices()
}

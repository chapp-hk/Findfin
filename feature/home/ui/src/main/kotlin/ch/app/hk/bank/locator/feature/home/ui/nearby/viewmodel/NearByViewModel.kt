package ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel

import ch.app.hk.bank.locator.core.design.ui.ScreenStateFlow
import ch.app.hk.bank.locator.feature.home.ui.nearby.model.NearByUiState

interface NearByViewModel {
    val uiState: ScreenStateFlow<NearByUiState.Service, NearByUiState.Error>

    fun getNearByServices()
}

package org.chapp.findfin.feature.home.presentation.ui.nearby.viewmodel

import kotlinx.coroutines.flow.StateFlow
import org.chapp.findfin.feature.home.presentation.ui.nearby.model.NearByUiState

internal interface NearByViewModel {
    val uiState: StateFlow<NearByUiState>

    fun getNearByServices()
}

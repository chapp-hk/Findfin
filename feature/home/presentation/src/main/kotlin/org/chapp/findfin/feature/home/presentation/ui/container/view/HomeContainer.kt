package org.chapp.findfin.feature.home.presentation.ui.container.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import org.chapp.findfin.feature.home.presentation.navigation.HomeEvent
import org.chapp.findfin.feature.home.presentation.ui.user.view.UserStatus

@Composable
internal fun HomeContainer(homeEvent: HomeEvent) {
    Column {
        UserStatus(onRequestAuth = homeEvent.onRequestAuth)
        HomeContainerContent(homeEvent = homeEvent)
    }
}

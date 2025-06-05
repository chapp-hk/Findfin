package org.chapp.findfin.feature.home.presentation.ui.container.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.chapp.findfin.feature.home.presentation.navigation.HomeEvent
import org.chapp.findfin.feature.home.presentation.ui.user.view.UserStatus

@Composable
internal fun HomeContainer(
    modifier: Modifier = Modifier,
    homeEvent: HomeEvent,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            UserStatus(onRequestAuth = homeEvent.onRequestAuth)
            HomeContainerContent(homeEvent = homeEvent)
        }
    }
}

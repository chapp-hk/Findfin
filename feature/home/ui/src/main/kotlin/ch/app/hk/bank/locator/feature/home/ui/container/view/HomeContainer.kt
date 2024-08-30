package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import ch.app.hk.bank.locator.feature.home.ui.user.view.UserStatus

@Composable
fun HomeContainer(
    onRequestAuth: () -> Unit,
    onSearch: (String) -> Unit,
) {
    Column {
        UserStatus(onRequestAuth = onRequestAuth)
        HomeContainerContent(onSearch = onSearch)
    }
}

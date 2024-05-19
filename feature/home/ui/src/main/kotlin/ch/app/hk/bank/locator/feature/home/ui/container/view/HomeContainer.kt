package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.runtime.Composable
import ch.app.hk.bank.locator.feature.home.ui.user.view.UserStatus

@Composable
internal fun HomeContainer(onRequestAuth: () -> Unit) {
    UserStatus(onRequestAuth = onRequestAuth)
}

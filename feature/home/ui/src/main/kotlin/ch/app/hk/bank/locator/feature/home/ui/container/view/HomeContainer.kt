package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.core.design.ui.search.AppSearchBar
import ch.app.hk.bank.locator.core.design.ui.search.rememberAppSearchBarState
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.finding.view.Finding
import ch.app.hk.bank.locator.feature.home.ui.user.view.UserStatus

@Composable
internal fun HomeContainer(
    onRequestAuth: () -> Unit,
    onSearch: (String) -> Unit,
) {
    Column {
        val searchBarState =
            rememberAppSearchBarState(
                placeholder = stringResource(id = R.string.home_placeholder_search),
            )

        UserStatus(onRequestAuth = onRequestAuth)

        AppSearchBar(
            state = searchBarState,
            onSearch = onSearch,
        )

        Finding(
            onFindYourBank = {},
            onFindBankOrAtms = {},
        )
    }
}

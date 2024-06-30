package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.core.design.ui.search.AppSearchBar
import ch.app.hk.bank.locator.core.design.ui.search.rememberAppSearchBarState
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.container.model.HomeItem
import ch.app.hk.bank.locator.feature.home.ui.finding.view.Finding

@Composable
internal fun HomeContainerContent(onSearch: (String) -> Unit) {
    val itemList =
        listOf(
            HomeItem.Search,
            HomeItem.Finding,
        )

    HomeContainerList(
        items = itemList,
        onSearch = onSearch,
    )
}

@Composable
private fun HomeContainerList(
    items: List<HomeItem>,
    onSearch: (String) -> Unit,
) {
    LazyColumn {
        itemsIndexed(
            items = items,
            key = { index, _ -> index },
        ) { _, item ->
            when (item) {
                HomeItem.Search -> {
                    val searchBarState =
                        rememberAppSearchBarState(
                            placeholder = stringResource(id = R.string.home_placeholder_search),
                        )

                    AppSearchBar(
                        state = searchBarState,
                        onSearch = onSearch,
                    )
                }

                HomeItem.Finding -> {
                    Finding(
                        onFindYourBank = {},
                        onFindBankOrAtms = {},
                    )
                }
            }
        }
    }
}

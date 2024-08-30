package ch.app.hk.bank.locator.feature.bank.ui.banklist.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.app.hk.bank.locator.core.design.ui.ScreenStateView
import ch.app.hk.bank.locator.core.design.ui.modifier.contentDescription
import ch.app.hk.bank.locator.feature.bank.ui.R
import ch.app.hk.bank.locator.feature.bank.ui.banklist.viewmodel.BankListViewModel
import ch.app.hk.bank.locator.feature.bank.ui.banklist.viewmodel.BankListViewModelImpl

@Composable
internal fun BankListBody(
    modifier: Modifier = Modifier,
    viewModel: BankListViewModel = hiltViewModel<BankListViewModelImpl>(),
) {
    ScreenStateView(
        state = viewModel.screenState.collectAsStateWithLifecycle(),
        success = { data ->
            BankListLazyColumn(
                modifier = modifier,
                data = data,
            )
        },
    )
}

@Composable
private fun BankListLazyColumn(
    modifier: Modifier = Modifier,
    data: List<String>,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = data,
            key = { item -> item },
        ) { item ->
            BankListItem(item = item)
        }
    }
}

@Composable
private fun BankListItem(item: String) {
    Column(
        modifier =
            Modifier
                .contentDescription(stringResource(id = R.string.bank_content_description_bank_item))
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .requiredSizeIn(minHeight = 48.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = item,
        )
    }
}

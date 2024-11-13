package org.chapp.findfin.feature.bank.presentation.ui.banklist.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.chapp.findfin.feature.bank.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BankListScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.bank_title_bank_list)) },
            )
        },
    ) { innerPadding ->
        BankListBody(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

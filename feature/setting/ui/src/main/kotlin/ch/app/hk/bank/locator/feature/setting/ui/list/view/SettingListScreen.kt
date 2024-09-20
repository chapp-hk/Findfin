package ch.app.hk.bank.locator.feature.setting.ui.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ch.app.hk.bank.locator.feature.setting.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingListScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.setting_tab_setting)) },
            )
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValue),
        ) {
            ThemePreference()
        }
    }
}

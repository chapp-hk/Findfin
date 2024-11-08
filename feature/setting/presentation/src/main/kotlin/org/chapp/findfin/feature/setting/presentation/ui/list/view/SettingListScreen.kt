package org.chapp.findfin.feature.setting.presentation.ui.list.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.chapp.findfin.feature.setting.presentation.R
import org.chapp.findfin.feature.setting.presentation.ui.list.view.language.LanguagePreference
import org.chapp.findfin.feature.setting.presentation.ui.list.view.theme.ThemePreference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingListScreen() {
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
            LanguagePreference()
        }
    }
}

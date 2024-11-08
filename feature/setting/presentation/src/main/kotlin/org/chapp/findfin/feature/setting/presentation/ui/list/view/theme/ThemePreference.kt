package org.chapp.findfin.feature.setting.presentation.ui.list.view.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.chapp.findfin.core.preferences.ui.foundation.ListPreference
import org.chapp.findfin.core.preferences.ui.foundation.ListPreferenceItem
import org.chapp.findfin.feature.setting.data.repo.preferece.model.Theme
import org.chapp.findfin.feature.setting.presentation.R

@Composable
internal fun ThemePreference(themePreferenceViewModel: ThemePreferenceViewModel = hiltViewModel()) {
    val themes =
        remember {
            listOf(
                Theme.SYSTEM to R.string.setting_theme_summary_system,
                Theme.LIGHT to R.string.setting_theme_summary_light,
                Theme.DARK to R.string.setting_theme_summary_dark,
            )
        }

    val list =
        themes.map { (theme, summaryResId) ->
            ListPreferenceItem(
                title = stringResource(id = summaryResId),
                value = theme.themeValue,
            )
        }

    val coroutineScope = rememberCoroutineScope()
    val selectedValue by themePreferenceViewModel.getCurrentTheme().collectAsStateWithLifecycle(initialValue = "")

    ListPreference(
        title = stringResource(id = R.string.setting_theme_title),
        list = list,
        selectedValue = { selectedValue },
        onChange = {
            coroutineScope.launch {
                themePreferenceViewModel.setTheme(it)
            }
        },
    )
}

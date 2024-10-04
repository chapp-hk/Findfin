package org.chapp.findfin.feature.setting.ui.list.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import org.chapp.findfin.core.preferences.ui.foundation.ListPreference
import org.chapp.findfin.core.preferences.ui.foundation.ListPreferenceItem
import org.chapp.findfin.feature.setting.data.repo.preferece.model.Theme
import org.chapp.findfin.feature.setting.ui.R
import org.chapp.findfin.feature.setting.ui.list.runtime.rememberThemePreferenceStore

@Composable
internal fun ThemePreference() {
    val list =
        remember {
            listOf(
                ListPreferenceItem(
                    titleRes = R.string.setting_theme_summary_system,
                    value = Theme.SYSTEM.themeValue,
                ),
                ListPreferenceItem(
                    titleRes = R.string.setting_theme_summary_light,
                    value = Theme.LIGHT.themeValue,
                ),
                ListPreferenceItem(
                    titleRes = R.string.setting_theme_summary_dark,
                    value = Theme.DARK.themeValue,
                ),
            )
        }

    val preferenceStore = rememberThemePreferenceStore()

    ListPreference(
        title = stringResource(id = R.string.setting_theme_title),
        list = list,
        preferenceStore = preferenceStore,
    )
}

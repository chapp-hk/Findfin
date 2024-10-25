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

    val preferenceStore = rememberThemePreferenceStore()

    ListPreference(
        title = stringResource(id = R.string.setting_theme_title),
        list = list,
        preferenceStore = preferenceStore,
    )
}

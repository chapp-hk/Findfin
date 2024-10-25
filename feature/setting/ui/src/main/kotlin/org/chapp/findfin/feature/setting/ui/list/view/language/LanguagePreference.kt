package org.chapp.findfin.feature.setting.ui.list.view.language

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.chapp.findfin.core.preferences.runtime.PreferenceStore
import org.chapp.findfin.core.preferences.ui.foundation.ListPreference
import org.chapp.findfin.core.preferences.ui.foundation.ListPreferenceItem
import org.chapp.findfin.feature.setting.ui.R

@Composable
internal fun LanguagePreference() {
    ListPreference(
        title = stringResource(id = R.string.setting_language_title),
        list =
            listOf(
                ListPreferenceItem(
                    title = stringResource(id = R.string.setting_theme_summary_system),
                    value = "",
                ),
                ListPreferenceItem(
                    title = "English",
                    value = "en",
                ),
                ListPreferenceItem(
                    title = "中文",
                    value = "zh",
                ),
            ),
        preferenceStore =
            object : PreferenceStore<String> {
                override fun get(): Flow<String> {
                    return flowOf()
                }

                override suspend fun set(value: String) {
                }
            },
    )
}

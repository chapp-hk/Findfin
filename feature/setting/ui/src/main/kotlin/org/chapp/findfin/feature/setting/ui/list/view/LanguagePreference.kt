package org.chapp.findfin.feature.setting.ui.list.view

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.chapp.findfin.core.preferences.runtime.PreferenceStore
import org.chapp.findfin.core.preferences.ui.foundation.ListPreference
import org.chapp.findfin.core.preferences.ui.foundation.ListPreferenceItem

@Composable
internal fun LanguagePreference() {
    ListPreference(
        title = "Language",
        list =
            listOf(
                ListPreferenceItem(
                    title = "English",
                    value = "en",
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

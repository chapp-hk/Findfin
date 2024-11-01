package org.chapp.findfin.feature.setting.ui.list.view.language

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.chapp.findfin.core.design.ui.foundation.text.UiText
import org.chapp.findfin.core.design.ui.foundation.text.asString
import org.chapp.findfin.core.preferences.runtime.PreferenceStore
import org.chapp.findfin.core.preferences.ui.foundation.ListPreference
import org.chapp.findfin.core.preferences.ui.foundation.ListPreferenceItem
import org.chapp.findfin.core.preferences.ui.foundation.Preference
import org.chapp.findfin.feature.setting.ui.R

@Composable
internal fun LanguagePreference(languagePreferenceViewModel: LanguagePreferenceViewModel = hiltViewModel()) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        LanguagePreferenceApi33(languagePreferenceViewModel)
    } else {
        LanguagePreferenceLegacy(languagePreferenceViewModel)
    }
}

internal data class LanguagePreferenceItem(
    val title: UiText,
    val value: String,
)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun LanguagePreferenceApi33(languagePreferenceViewModel: LanguagePreferenceViewModel) {
    val context = LocalContext.current
    Preference(
        modifier =
            Modifier.clickable {
                val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
                intent.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(intent)
            },
        title = stringResource(id = R.string.setting_language_title),
        description = languagePreferenceViewModel.getCurrentLanguageName().asString(),
    )
}

@Composable
private fun LanguagePreferenceLegacy(languagePreferenceViewModel: LanguagePreferenceViewModel) {
    ListPreference(
        title = stringResource(id = R.string.setting_language_title),
        list =
            languagePreferenceViewModel.availableLanguages
                .map { language ->
                    ListPreferenceItem(
                        title = language.title.asString(),
                        value = language.value,
                    )
                },
        preferenceStore =
            object : PreferenceStore<String> {
                override fun get(): Flow<String> {
                    return flowOf(languagePreferenceViewModel.getCurrentLanguageTag())
                }

                override suspend fun set(value: String) {
                    languagePreferenceViewModel.setLanguage(value)
                }
            },
    )
}

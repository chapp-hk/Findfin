package ch.app.hk.bank.locator.core.preferences.impl

import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject

class PreferenceKey @Inject constructor() {
    val appLocale = stringPreferencesKey("app_locale")
}

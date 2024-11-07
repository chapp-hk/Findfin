package org.chapp.findfin.feature.setting.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.LocaleManagerCompat

class LanguageChangedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
            val tags = LocaleManagerCompat.getApplicationLocales(context).toLanguageTags()
            Toast.makeText(context, "Language changed: $tags", Toast.LENGTH_SHORT).show()
        }
    }
}

package org.chapp.findfin.feature.setting.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class LanguageChangedBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_LOCALE_CHANGED) {
            val workRequest = OneTimeWorkRequestBuilder<ChangeLanguageWorker>().build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}

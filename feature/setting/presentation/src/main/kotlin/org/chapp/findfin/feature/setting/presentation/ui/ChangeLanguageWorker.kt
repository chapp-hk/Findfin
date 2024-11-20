package org.chapp.findfin.feature.setting.presentation.ui

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ChangeLanguageWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("ChangeLanguageWorker", "doWork: ")
        return Result.success()
    }
}

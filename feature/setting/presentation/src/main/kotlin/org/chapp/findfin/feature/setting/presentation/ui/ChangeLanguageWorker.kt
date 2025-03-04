package org.chapp.findfin.feature.setting.presentation.ui

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import org.chapp.findfin.feature.setting.domain.fetch.usecase.FetchAllBankLocationsWithLanguageUseCase

@HiltWorker
class ChangeLanguageWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val fetchAllBankLocationsWithLanguage: FetchAllBankLocationsWithLanguageUseCase,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        fetchAllBankLocationsWithLanguage("zh")
        return Result.success()
    }
}

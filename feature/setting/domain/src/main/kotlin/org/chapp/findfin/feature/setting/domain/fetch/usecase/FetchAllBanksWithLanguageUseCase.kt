package org.chapp.findfin.feature.setting.domain.fetch.usecase

interface FetchAllBanksWithLanguageUseCase {
    suspend operator fun invoke(): Boolean
}

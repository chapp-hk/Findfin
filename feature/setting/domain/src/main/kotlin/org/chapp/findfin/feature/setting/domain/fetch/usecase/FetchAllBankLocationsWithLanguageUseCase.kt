package org.chapp.findfin.feature.setting.domain.fetch.usecase

interface FetchAllBankLocationsWithLanguageUseCase {
    suspend operator fun invoke(): Boolean
}
